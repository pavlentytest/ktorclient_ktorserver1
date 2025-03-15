package com.example.myapplication.remote

import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getUsers() : Flow<ApiResult<List<User>>>
    fun addUser(name: String, age: Int) : Flow<ApiResult<String>>
    fun getUser(id: Int): Flow<ApiResult<User>>
}