package com.hybrid.appointment.ui.screen.appointment.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.GetAddressFromLocationUseCase
import com.hybrid.appointment.domain.appointment.usecase.InsertAppointmentUseCase
import com.hybrid.appointment.domain.appointment.validate.ValidateRequiredUseCase
import com.hybrid.appointment.domain.appointment.validate.ValidateTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentFormVM @Inject constructor(
    private val insertAppointmentUseCase: InsertAppointmentUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateRequiredUseCase: ValidateRequiredUseCase,
    private val getAddressFromLocationUseCase: GetAddressFromLocationUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<AppointmentFormUiState> = MutableStateFlow(AppointmentFormUiState())
    val state: StateFlow<AppointmentFormUiState> get() = _state

    private val _events: MutableSharedFlow<Unit> = MutableSharedFlow()
    val events: SharedFlow<Unit> get() = _events.asSharedFlow()


    fun canSave(): Boolean{
        val appointment = state.value.appointmentError
        return appointment.titleError == true && appointment.dateError == true &&
                appointment.timeError == true && appointment.latError == true &&
                appointment.lonError == true
    }

    fun onAction(action: AppointmentFormAction){
        when(action){
            is AppointmentFormAction.DateChanged -> {
                val date = action.date
                val error = validateRequiredUseCase(date)
                _state.update {
                    it.copy(
                        appointment = it.appointment.copy(date = date),
                        appointmentError = it.appointmentError.copy(dateError = error)
                    )
                }
            }
            is AppointmentFormAction.LocationChanged -> {
                val lat = action.lat
                val lon = action.lon
                val error = true
                viewModelScope.launch {
                    val address = getAddressFromLocationUseCase(lat, lon)
                    _state.update {
                        it.copy(
                            appointment = it.appointment.copy(lat = lat, lon = lon, address = address),
                            appointmentError = it.appointmentError.copy(latError = error, lonError = error)
                        )
                    }
                }
            }
            is AppointmentFormAction.TimeChanged -> {
                val time = action.time
                val error = validateRequiredUseCase(time)
                _state.update {
                    it.copy(
                        appointment = it.appointment.copy(time = time),
                        appointmentError = it.appointmentError.copy(timeError = error)
                    )
                }
            }
            is AppointmentFormAction.TitleChanged -> {
                val title = action.title
                val error = validateTitleUseCase(title)
                _state.update {
                    it.copy(
                        appointment = it.appointment.copy(title = title),
                        appointmentError = it.appointmentError.copy(titleError = error)
                    )
                }
            }
        }
    }

    fun onSave(){
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = insertAppointmentUseCase(state.value.appointment)
            when (result){
                is Result.Error -> {
                    _state.update {
                        it.copy(errorMessage = result.message)
                    }
                }
                is Result.Success<*> -> {
                    _events.emit(Unit)
                }
            }
        }
    }

}