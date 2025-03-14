package com.azul.azulVentas.ui.presentation.userPG.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azul.azulVentas.domain.model.userPG.UserPG
import com.azul.azulVentas.domain.usecases.userPG.GetUserPGEmailUseCase
import com.azul.azulVentas.domain.usecases.userPG.InsertUserPGUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.azul.azulVentas.core.utils.Result


@HiltViewModel
class UserPGViewModel @Inject constructor(
    private val getUserPGEmailUseCase: GetUserPGEmailUseCase,
    private val insertUserPGUseCase: InsertUserPGUseCase
) : ViewModel() {

    private val _userPG = MutableLiveData<List<UserPG>>()
    val userPG: LiveData<List<UserPG>> = _userPG

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun findUserPG(usu_email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _userPG.value = getUserPGEmailUseCase(usu_email)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val initialUserPG = UserPG(
        EMPRESA_ID = -1, // Use a placeholder value
        USU_NOMBRE = "",
        USU_APELLIDO = "",
        USU_TELEFONO = "",
        USU_EMAIL = "",
        USU_PASS = "",
        USU_USUARIO = ""
    )

    // Initialize with Result.Success holding the dummy UserPG
    private val _insertUserState = MutableStateFlow<Result<UserPG>>(Result.Success(initialUserPG))
    val insertUserState: StateFlow<Result<UserPG>> = _insertUserState

    fun insertUserPG(userPG: UserPG) {
        viewModelScope.launch {
            _insertUserState.value = insertUserPGUseCase(userPG)
        }
    }
    //In case you need to clear the result
    fun clearInsertResult() {
        _insertUserState.value = Result.Success(initialUserPG)
    }
}

