package com.hybrid.appointment.ui.components.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Loading(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Loading...",
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )
    }
}