package com.example.pokedex.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.presentation.theme.PokedexTheme
import com.example.pokedex.presentation.theme.colorForType

@Composable
fun TypeChip(
    type: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorForType(type)
) {
    Text(
        text = type,
        color = Color.White,
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun TypeChipPreview() {
    PokedexTheme {
        TypeChip(type = "Grass")
    }
}
