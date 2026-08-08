package com.danamansour.engineerhub

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class WavyHeaderShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - 40f)
            // Draws a smooth curve across the bottom edge
            quadraticTo(
                size.width / 2f, size.height + 40f,
                size.width, size.height - 40f
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}