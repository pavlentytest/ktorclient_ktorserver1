package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val resultLiveData = MutableLiveData<String>("")
    var repository: MainRepository = MainRepository()

    fun addUser(name:String, age:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMethod(name,age)
        }
    }
    fun getUser(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getByIdMethod(id, resultLiveData)
        }
    }
    fun listUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.listAllMethod(resultLiveData)
        }
    }
}