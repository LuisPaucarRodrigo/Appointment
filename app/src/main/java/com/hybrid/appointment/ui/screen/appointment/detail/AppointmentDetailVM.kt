package com.hybrid.appointment.ui.screen.appointment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.AppointmentState
import com.hybrid.appointment.domain.appointment.usecase.GetAppointmentByIdUseCase
import com.hybrid.appointment.domain.appointment.usecase.GetRouteUseCase
import com.hybrid.appointment.domain.appointment.usecase.UpdateAppointmentUseCase
import com.hybrid.appointment.domain.settings.GpsState
import com.hybrid.appointment.domain.settings.LocationSettingsCheckerUseCase
import com.hybrid.appointment.domain.settings.StartLocationUpdatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentDetailVM @Inject constructor(
    private val getAppointmentByIdUseCase: GetAppointmentByIdUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val locationSettingsCheckerUseCase: LocationSettingsCheckerUseCase,
    private val startLocationUpdatesUseCase: StartLocationUpdatesUseCase,
    private val getRouteUseCase: GetRouteUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AppointmentDetailUiState> = MutableStateFlow(AppointmentDetailUiState())
    val state: StateFlow<AppointmentDetailUiState> get() = _state

    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation: StateFlow<LatLng?> = _userLocation

    private val _route: MutableStateFlow<List<List<LatLng>>> = MutableStateFlow<List<List<LatLng>>>(emptyList())
    val route: StateFlow<List<List<LatLng>>> = _route

    //UI
    private val _uiGps:MutableSharedFlow<ResolveGps> = MutableSharedFlow<ResolveGps>()
    val uiGps: SharedFlow<ResolveGps> get() = _uiGps

    private val _uiError:MutableSharedFlow<ShowError> = MutableSharedFlow<ShowError>()
    val uiError: SharedFlow<ShowError> get() = _uiError

    private val _canShowLocation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val canShowLocation: StateFlow<Boolean> = _canShowLocation

    private val _appointmentId: MutableStateFlow<Long?> = MutableStateFlow(null)

    init {
        getAppointment()
    }

    fun getAppointment (){
        viewModelScope.launch {
            _appointmentId
                .filterNotNull()
                .flatMapLatest {
                    getAppointmentByIdUseCase(it)
                }
                .collect { appointment ->
                    _state.update { it.copy(appointment = appointment, isLoading = false) }
                }
        }
    }

    fun setAppointmentId(id:Long){
        _appointmentId.value = id
    }

    fun updateAppointment() {
        viewModelScope.launch {
            val current = state.value.appointment ?: return@launch
            val newState = when (current.state) {
                AppointmentState.NO_VISITADO -> AppointmentState.EN_RUTA
                AppointmentState.EN_RUTA -> AppointmentState.VISITADO
                AppointmentState.VISITADO -> AppointmentState.VISITADO
            }
            val updatedAppointment = current.copy(state = newState)

            val result = updateAppointmentUseCase(updatedAppointment)

            when (result) {
                is Result.Success -> { Unit }
                is Result.Error -> {
                    _state.update {
                        it.copy(error = result.message)
                    }
                }
            }
        }
    }
    
    fun showError(error: String){
        viewModelScope.launch { _uiError.emit(ShowError(error)) }
    }

    fun startLocationUpdates() {
        viewModelScope.launch {
            startLocationUpdatesUseCase().collect { _userLocation.value = it }
        }
    }

    fun getAllRoutes() {
        viewModelScope.launch {
                combine(userLocation, state) { location, state ->
                    val appointment = state.appointment
                    if (location != null && appointment != null) {
                        location to LatLng(appointment.lat, appointment.lon)
                    } else null
                }
                .filterNotNull()
                .collect { (start, end) ->
                    val result = getRouteUseCase(start, end)
                    when(result) {
                        is Result.Success -> _route.value = result.data
                        is Result.Error -> _state.update { it.copy(error = result.message) }
                    }
                }
        }
    }

    fun onPermissionResult(state: Boolean){
        if (state) checkGps() else viewModelScope.launch { _uiError.emit(ShowError("Permits are required for the tracking")) }
    }

    fun checkGps(){
        viewModelScope.launch {
            val result = locationSettingsCheckerUseCase()
            when(result) {
                GpsState.Disabled -> {
                    _canShowLocation.value = false
                    _uiError.emit(ShowError("Your GPS cannot be used. Please check."))
                }
                GpsState.Enabled -> {
                    _canShowLocation.value = true
                    startLocationUpdates()
                    getAllRoutes()
                }
                GpsState.Idle -> {
                    _canShowLocation.value = true
                }
                is GpsState.NeedsResolution -> { _uiGps.emit(ResolveGps(result.exception)) }
            }
        }
    }

    fun clearAllState() {
        _state.value = AppointmentDetailUiState()
        _userLocation.value = null
        _appointmentId.value = null
        _route.value = emptyList()
    }
}