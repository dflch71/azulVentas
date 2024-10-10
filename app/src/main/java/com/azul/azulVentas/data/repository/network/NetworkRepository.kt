package com.azul.azulVentas.data.repository.network

interface NetworkRepository {
    fun isNetworkAvailable(): Boolean
}