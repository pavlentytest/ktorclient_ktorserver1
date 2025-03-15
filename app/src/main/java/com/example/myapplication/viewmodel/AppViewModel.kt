package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.User
import com.example.myapplication.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val apiService: ApiService,
                                       private val defaultDispatcher: CoroutineDispatcher): ViewModel() {
    private val _users = MutableStateFlow<ApiResult<List<User>>>(ApiResult.Loading())
    val users = _users.asStateFlow()

    private val _user = MutableStateFlow<ApiResult<User>>(ApiResult.Loading())
    val user = _user.asStateFlow()

    private val _id = MutableStateFlow<ApiResult<String>>(ApiResult.Loading())
    val id = _id.asStateFlow()

    fun fetchUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            apiService.getUsers()
                .flowOn(defaultDispatcher)
                .catch {
                    _users.value=ApiResult.Error(it.message ?: "Something went wrong fetchUsers()")
                }
                .collect{
                    _users.value=it
                }
        }
    }

    fun fetchUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.getUser(id)
                .flowOn(defaultDispatcher)
                .catch {
                    _user.value=ApiResult.Error(it.message ?: "Something went wrong fetchUser()")
                }
                .collect{
                    _user.value=it
                }
        }
    }

    fun sendUser(name: String, age: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            apiService.addUser(name, age)
                .flowOn(defaultDispatcher)
                .catch {
                    _id.value=ApiResult.Error(it.message ?: "Something went wrong sendUser()")
                }
                .collect{
                    _id.value=it
                }
        }
    }
}