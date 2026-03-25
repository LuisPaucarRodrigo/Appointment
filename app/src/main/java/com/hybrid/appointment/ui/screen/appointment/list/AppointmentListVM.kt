package com.hybrid.appointment.ui.screen.appointment.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hybrid.appointment.domain.Result
import com.hybrid.appointment.domain.appointment.usecase.DeleteAppointmentUseCase
import com.hybrid.appointment.domain.appointment.usecase.GetAllAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    init { getAllAppointment() }

    private fun getAllAppointment(){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getAllAppointmentsUseCase().collect { result ->
                _state.update {
                    it.copy(appointments = result, isLoading = false)
                }
            }
        }
    }

    fun deleteAppointment(id:Long){
        viewModelScope.launch {
            val result = deleteAppointmentUseCase(id)
            when(result){
                is Result.Error -> _state.update { it.copy(error = result.message) }
                is Result.Success<*> -> { }
            }
        }
    }
}