package com.azul.azulVentas.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.Shop2
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PointOfSale
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.Shop2
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.azul.azulVentas.R

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
    //onItemselected: (selectedIndex: Int) -> Unit
){

    //var selectedItemIndex by remember { mutableStateOf(0) }

    val navigationList = listOf(
        NavigationItem(
            title = "Ventas",
            selectedIcon = Icons.Filled.Paid,
            unselectedIcon = Icons.Outlined.Paid
        ),

        NavigationItem(
            title = "POS",
            selectedIcon = Icons.Filled.PointOfSale,
            unselectedIcon = Icons.Outlined.PointOfSale
        ),

        NavigationItem(
            title = "Compras",
            selectedIcon = Icons.Filled.ShoppingBag,
            unselectedIcon = Icons.Outlined.ShoppingBag
        ),

        NavigationItem(
            title = "Egresos",
            selectedIcon = Icons.Filled.BusinessCenter,
            unselectedIcon = Icons.Outlined.BusinessCenter
        ),
    )

    NavigationBar {
        navigationList.forEachIndexed{ index, navigationItem ->
            NavigationBarItem(
                label = { Text(text = navigationItem.title) },
                selected = index == selectedItemIndex,
                onClick = {
                    //selectedItemIndex = index
                    //onItemselected(selectedItemIndex)

                    onItemSelected(index)
                },
                icon = {
                    Icon(
                        imageVector = if(index == selectedItemIndex) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                        contentDescription = navigationItem.title
                    )
                }
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)