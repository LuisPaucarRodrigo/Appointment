package com.hybrid.appointment.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.hybrid.appointment.ui.components.layouts.Loading
import com.hybrid.appointment.core.ui.TopBar
import com.hybrid.appointment.ui.screen.list.AppointmentItem

@Composable
fun AppointmentHistoryScreen(
    appointmentHistoryVM: AppointmentHistoryVM = hiltViewModel()
){
    val appointments = appointmentHistoryVM.uiState.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        appointmentHistoryVM.events.collect { event ->
            when(event){
                is AppointmentHistoryEvent.Error -> {}
            }
        }
    }

    Scaffold(
        topBar = { TopBar() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)

        ) {
            if (appointments.loadState.refresh == LoadState.Loading) {
                item {
                    Loading()
                }
            }

            if (appointments.loadState.refresh is LoadState.Error) {
                item {
                    // UI element for error
                }
            }

            items(count = appointments.itemCount) { index ->
                val article = appointments[index]
                article?.let {
                    AppointmentItem(article)
                }
            }

            if (appointments.loadState.append == LoadState.Loading) {
                item {
                    Loading()
                }
            }

            if (appointments.loadState.append.endOfPaginationReached) {
                item {
                    // UI element for last page
                }
            }
        }
    }
}

