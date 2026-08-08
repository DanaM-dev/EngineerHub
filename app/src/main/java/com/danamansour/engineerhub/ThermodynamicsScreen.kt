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
fun ThermodynamicsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        IdealGasLawCalculator()
        SectionDivider()
        ConductionHeatCalculator()
        SectionDivider()
        ConvectionHeatCalculator()
        SectionDivider()
        CarnotEfficiencyCalculator()
        SectionDivider()
        ReynoldsNumberCalculator()
        SectionDivider()
        BernoulliPressureCalculator()
        SectionDivider()
        FlowRateCalculator()
    }
}