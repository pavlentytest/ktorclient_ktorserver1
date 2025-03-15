package com.example.myapplication

import androidx.lifecycle.MutableLiveData

class MainRepository {
    val ktorService: KtorService = KtorService()

    suspend fun addMethod(name: String, age: Int){
        ktorService.addUser(name, age)
    }
    suspend fun getByIdMethod(id: Int, resultLD: MutableLiveData<String>){
        ktorService.getUser(id, resultLD)
    }
    suspend fun listAllMethod(resultLD:MutableLiveData<String>){
        ktorService.userList(resultLD)
    }
}