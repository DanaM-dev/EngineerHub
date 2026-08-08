package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun SectionDivider() {
    Divider(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), color = Color(0xFFEEEEEE))
}


@Composable
fun EngineerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isTextOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSoftGray) },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GradientEnd,
            unfocusedBorderColor = Color(0xFFE0E0E0)
        ),
        keyboardOptions = if (isTextOnly) KeyboardOptions.Default else KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
@Composable
fun EngineerCalculatorTemplate(  //params
    title: String,
    subtitle: String,
    onCalculate: () -> Unit,
    resultText: String,
    isError: Boolean = false,
    inputContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Light,
            fontSize = 24.sp,
            color = TextPrimaryDark
        )
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = TextSoftGray
        )
        Spacer(modifier = Modifier.height(24.dp))

        // injects the input fields
        inputContent()

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCalculate,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = CalendarHighlight
            )
        ) {
            Text(text = "Calculate", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resultText.isNotEmpty()) {
            Text(
                text = resultText,
                fontSize = 20.sp,
                color = if (isError) Color(0xFFE57373) else TextPrimaryDark,
                fontWeight = FontWeight.Medium
            )
        }
    }
}