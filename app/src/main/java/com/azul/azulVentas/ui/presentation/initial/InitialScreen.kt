package com.azul.azulVentas.ui.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azul.azulVentas.R
import com.azul.azulVentas.ui.theme.BackgroundButton
import com.azul.azulVentas.ui.theme.Black
import com.azul.azulVentas.ui.theme.Gray
import com.azul.azulVentas.ui.theme.Green
import com.azul.azulVentas.ui.theme.ShapeButton

@Preview
@Composable
fun InitialScreen( navigateToLogin: () -> Unit = {}, navigateToSingUp: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray, Black), startY = 0f, endY = 600f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.spotify),
            contentDescription = "",
            modifier = Modifier.clip(CircleShape)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text("Millions of songs.",
            color = White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Text("Free on Spotify.",
            color = White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateToSingUp() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = ButtonDefaults.buttonColors(Green)
        ) {
            Text(text = "Sing up free", color = Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(Modifier.clickable {  } , painterResource(id = R.drawable.google) , title = "Continue with Google" )

        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(Modifier.clickable {  } , painterResource(id = R.drawable.facebook) , title = "Continue with Facebook" )

        Text(
            text = "Log In",
            color = White,
            modifier = Modifier.padding(24.dp).clickable { navigateToLogin() } ,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

    }

}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(BackgroundButton)
            .border(2.dp, ShapeButton, CircleShape),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}