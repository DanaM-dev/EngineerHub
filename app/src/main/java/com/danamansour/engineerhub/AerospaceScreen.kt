package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AerospaceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        AerodynamicLiftDragCalculator()
        SectionDivider()
        StandardAtmosphereCalculator()
        SectionDivider()
        RocketThrustCalculator()
        SectionDivider()
        AqiCalculator()
        SectionDivider()
        NoiseLevelCalculator()
    }
}