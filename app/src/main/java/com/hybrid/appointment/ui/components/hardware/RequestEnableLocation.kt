package com.hybrid.appointment.ui.components.hardware

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.android.gms.common.api.ResolvableApiException

@Composable
fun RequestEnableLocation(
    exception: ResolvableApiException,
    onGpsEnabled: () -> Unit,
    onGpsDisabled: () -> Unit
) {
    val resolutionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onGpsEnabled()
        } else {
            onGpsDisabled()
        }
    }

    LaunchedEffect(exception) {
        val intentSenderRequest =
            IntentSenderRequest.Builder(exception.resolution).build()

        resolutionLauncher.launch(intentSenderRequest)
    }
}