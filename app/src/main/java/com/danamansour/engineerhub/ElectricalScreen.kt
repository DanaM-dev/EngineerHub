package com.danamansour.engineerhub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ElectricalScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        VoltageCalculator()
        SectionDivider()
        SeriesComponentCalculator()
        SectionDivider()
        ParallelComponentCalculator()
        SectionDivider()
        VoltageDividerCalculator()
        SectionDivider()
        TimeConstantCalculator()
        SectionDivider()
        ResonantFrequencyCalculator()
        SectionDivider()
        ResistorColorCodeCalculator()
        SectionDivider()
        ACPowerCalculator()
        SectionDivider()
        OpAmpGainCalculator()
        SectionDivider()
        PCBTraceWidthCalculator()
    }
}