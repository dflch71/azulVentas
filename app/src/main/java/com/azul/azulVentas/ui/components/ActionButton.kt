package com.azul.azulVentas.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.theme.AzulVentasTheme
import com.azul.azulVentas.ui.theme.PrimaryPink

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    isNavigationArrowVisible: Boolean,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit,
    colors: ButtonColors,
    shadowColor: Color
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(percent = 50),
                spotColor = shadowColor
            ),
        onClick =  onClicked,
        enabled = true,
        shape = RoundedCornerShape(percent = 50),
        colors = colors
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            if (isNavigationArrowVisible) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .combinedClickable(
                            onClick = onClicked,
                            onLongClick = onLongClicked
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun ActionButtonPreview_NavigationVisible() {
    AzulVentasTheme {
        ActionButton(
            text = "Action text",
            isNavigationArrowVisible = true,
            onClicked = {},
            onLongClicked = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryPink
            ),
            shadowColor = PrimaryPink
        )
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    AzulVentasTheme {
        ActionButton(
            text = "Action text",
            isNavigationArrowVisible = false,
            onClicked = {},
            onLongClicked = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = PrimaryPink
            ),
            shadowColor = PrimaryPink
        )
    }
}