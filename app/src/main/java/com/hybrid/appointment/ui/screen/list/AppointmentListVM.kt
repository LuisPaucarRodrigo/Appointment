package com.hybrid.appointment.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.usecase.DeleteAppointmentUseCase
import com.hybrid.appointment.domain.appointment.usecase.GetAllAppointmentsUseCase
import com.hybrid.appointment.ui.screen.detail.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentListVM @Inject constructor(
    private val getAllAppointmentsUseCase: GetAllAppointmentsUseCase,
    private val deleteAppointmentUseCase: DeleteAppointmentUseCase
): ViewModel() {
    private val _state: MutableStateFlow<AppointmentListUiState> = MutableStateFlow(AppointmentListUiState())
    val state: StateFlow<AppointmentListUiState> get() = _state

    private val _events: MutableSharedFlow<AppointmentListEvent> = MutableSharedFlow()
    val events: SharedFlow<AppointmentListEvent> get() = _events

    init { getAllAppointment() }

    private fun getAllAppointment(){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getAllAppointmentsUseCase().collect { result ->
                when(result){
                    is Result.Success -> {
                        _state.update {
                            it.copy(appointments = result.data.map { e -> e.toUi() }, isLoading = false)
                        }
                    }
                    is Result.Error -> {
                        _events.emit(AppointmentListEvent.Error(result.message))
                    }
                }
            }
        }
    }

    fun deleteAppointment(id:Long){
        viewModelScope.launch {
            val result = deleteAppointmentUseCase(id)
            when(result){
                is Result.Error -> {
                    _events.emit(AppointmentListEvent.Error(result.message))
                }
                is Result.Success<*> -> {
                    _events.emit(AppointmentListEvent.Success("Se cancelo exitosamente"))
                }
            }
        }
    }
}