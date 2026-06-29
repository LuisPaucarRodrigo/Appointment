package com.hybrid.appointment.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    secondOnClick: (() -> Unit)? = null,
    placeholder: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 17.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(13.dp))
                .border(
                    width = 1.dp,
                    color = if (isError) Color.Red else Color.LightGray,
                    shape = RoundedCornerShape(13.dp)
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            leadingIcon?.let {
                it.invoke()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Box {
                    BasicTextField(
                        value = value ?: "",
                        enabled = enabled,
                        readOnly = readOnly,
                        onValueChange = onValueChange,
                        singleLine = singleLine,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = keyboardOptions,
                        visualTransformation = visualTransformation,
                        decorationBox = { innerTextField ->

                            if (value == "" || value == null) {
                                Text(
                                    text = placeholder,
                                    color = Color.Gray
                                )
                            }

                            innerTextField()
                        }
                    )
                    secondOnClick?.let {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable {
                                    secondOnClick()
                                }
                                .background(Color.Transparent)
                        )
                    }
                }
            }

            trailingIcon?.invoke()
        }
    }
}