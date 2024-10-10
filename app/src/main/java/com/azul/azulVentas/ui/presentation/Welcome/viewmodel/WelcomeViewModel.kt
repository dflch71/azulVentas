package com.azul.azulVentas.ui.presentation.Welcome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azul.azulVentas.domain.usecases.network.CheckNetworkUseCase
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val checkNetworkUseCase: CheckNetworkUseCase
): ViewModel() {

    private var _isNetworkAvailable = MutableLiveData<Boolean?>()
    val isNetworkAvailable: LiveData<Boolean?> = _isNetworkAvailable

    fun checkNetwork() {
        _isNetworkAvailable.value = checkNetworkUseCase()
    }

}