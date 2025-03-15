package com.example.myapplication.remote

import com.example.myapplication.Util.ALL_SUB_URL
import com.example.myapplication.Util.BASE_URL
import com.example.myapplication.Util.USERS_SUB_URL
import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override fun getUsers(): Flow<ApiResult<List<User>>> = flow{
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("$BASE_URL$USERS_SUB_URL$ALL_SUB_URL").body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong getUsers()"))
        }
    }

    override fun addUser(name: String, age: Int) : Flow<ApiResult<String>> = flow {
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.post("$BASE_URL$USERS_SUB_URL"){
                setBody(User(name, age))
            }.body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong addUser()"))
        }
    }

    override fun getUser(id: Int): Flow<ApiResult<User>> = flow{
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("$BASE_URL$USERS_SUB_URL/$id").body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong getUser()"))
        }
    }
}
