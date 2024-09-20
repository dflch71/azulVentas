package com.azul.azulVentas.modClientes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreeen(){

    var userName by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start

    ) {

        Text (text = "Your user name is $userName")

        TextField(
            value = userName,
            onValueChange = { newvalue ->
                userName = newvalue
            },
            label = { Text(text = "Add your User name") },
            placeholder = { Text(text = "User name") },
            singleLine = true,
            isError = userName.isBlank()
        )

        Spacer(modifier = Modifier.height(20.dp))

        var composeSelected by remember {
            mutableStateOf(false)
        }

        var xmlSelected by remember {
            mutableStateOf(false)
        }

        Row{
            FilterChip(
                selected = composeSelected,
                onClick = { composeSelected = !composeSelected },
                label = {
                    Text(text = "Compose")
                },
                leadingIcon = {
                    AnimatedVisibility(visible = composeSelected) {
                        Icon(Icons.Filled.Done, contentDescription = null)
                    }
                }
            )
            Spacer(modifier = Modifier.width(15.dp))
            FilterChip(
                selected = xmlSelected,
                onClick = { xmlSelected = !xmlSelected },
                label = {
                    Text(text = "XML")
                },
                leadingIcon = {
                    AnimatedVisibility(visible = xmlSelected) {
                        Icon(Icons.Filled.Done, contentDescription = null)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        var sliderValue by remember {
            mutableStateOf(1f)
        }

        Text(text = "Composable Slider ${sliderValue.toInt()}")

        Slider(
            value = sliderValue,
            onValueChange = {newValue ->
                sliderValue = newValue
            },
            valueRange = 1f..5f,
            steps = 4
        )

        Spacer(modifier = Modifier.height(20.dp))

        var darkModeenabled by remember {
            mutableStateOf(true)
        }


        Text(text = "Dark Mode $darkModeenabled")

        Switch(
            thumbContent = {
                AnimatedVisibility(visible = darkModeenabled) {
                    Icon(Icons.Filled.Done, contentDescription = null)
                }
            },
            checked = darkModeenabled,
            onCheckedChange = { newValue ->
                darkModeenabled = newValue
            }
        )
    }
}