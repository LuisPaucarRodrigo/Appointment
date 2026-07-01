package com.hybrid.appointment.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.hybrid.appointment.core.utils.Result
import com.hybrid.appointment.domain.appointment.usecase.GetAppointmentHistoryUseCase
import com.hybrid.appointment.ui.screen.detail.AppointmentUi
import com.hybrid.appointment.ui.screen.detail.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentHistoryVM @Inject constructor(
    private val getAppointmentHistoryUseCase: GetAppointmentHistoryUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<PagingData<AppointmentUi>> = MutableStateFlow(PagingData.empty())
    val uiState: StateFlow<PagingData<AppointmentUi>> get() = _uiState

    private val _events: MutableSharedFlow<AppointmentHistoryEvent> = MutableSharedFlow()
    val events: SharedFlow<AppointmentHistoryEvent> get() = _events

    init {
        getAppointmentHistory()
    }

    fun getAppointmentHistory(){
        viewModelScope.launch {
            getAppointmentHistoryUseCase()
                .collect { result ->
                    when(result){
                        is Result.Success -> {
                            _uiState.value = result.data.map { it.toUi() }
                        }
                        is Result.Error -> {
                            _events.emit(AppointmentHistoryEvent.Error(result.message))
                        }
                    }
                }
        }
    }
}