package com.azul.azulVentas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage

@Composable
fun FavoritesScreen() {
    val image1 =
        "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c2VhJTIwYmVhY2h8ZW58MHx8MHx8fDA%3D"

    val image2 =
        "https://images.unsplash.com/photo-1520520731457-9283dd14aa66?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c2VhJTIwYmVhY2h8ZW58MHx8MHx8fDA%3D"

    val image3 =
        "https://images.unsplash.com/photo-1516815231560-8f41ec531527?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fHNlYSUyMGJlYWNofGVufDB8fDB8fHww"

    val image4 =
        "https://images.unsplash.com/photo-1559494007-9f5847c49d94?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fHNlYSUyMGJlYWNofGVufDB8fDB8fHww"

    val image5 =
        "https://images.unsplash.com/photo-1520454974749-611b7248ffdb?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzB8fHNlYSUyMGJlYWNofGVufDB8fDB8fHww"

    val image6 =
        "https://images.unsplash.com/photo-1531514381259-8c9fedc910b8?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDB8fHNlYSUyMGJlYWNofGVufDB8fDB8fHww"

    val image7 =
        "https://images.unsplash.com/photo-1543824634-43f66beda35c?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Njh8fHNlYSUyMGJlYWNofGVufDB8fDB8fHww"


    val seaList = remember {
        mutableStateListOf(
            Sea(name ="Sea 1", image = image1),
            Sea(name ="Sea 2", image = image2),
            Sea(name ="Sea 3", image = image3),
            Sea(name ="Sea 4", image = image4),
            Sea(name ="Sea 5", image = image5),
            Sea(name ="Sea 6", image = image6),
            Sea(name ="Sea 7", image = image7)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), content = {

            itemsIndexed(seaList) { index, sea ->
                Card(
                    modifier = Modifier.size(200.dp)
                ){
                    Box(modifier = Modifier.fillMaxSize()
                    ){
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),

                            modifier = Modifier
                                .zIndex(1f)
                                .align(Alignment.TopEnd),

                            onClick = {
                                seaList.removeAt(index)
                                seaList.add(index, sea.copy(isFavorite = !sea.isFavorite))
                            }
                        ){
                            Icon(imageVector = if (sea.isFavorite) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            contentDescription = null )
                        }

                        AsyncImage(
                            model = sea.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                        ){
                            Text(
                                text = sea.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(8.dp)
                                    .zIndex(1f)
                            )
                        }
                    }
                }
            }
        }
    )
}

data class Sea(
    val name: String,
    val image: String,
    val isFavorite: Boolean = false
)