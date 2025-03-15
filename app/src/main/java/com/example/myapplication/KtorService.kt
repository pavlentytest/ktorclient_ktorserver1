package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

const val connectionURL: String = "http://192.168.76.38:8080"
const val usersSubURL: String = "/users"
const val allSubURL: String = "/all"

class KtorService {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val client = HttpClient{
        install(ContentNegotiation) {
            json()
        }
    }
    suspend fun addUser(name: String, age: Int) {
        val httpResponse = client.post("$connectionURL$usersSubURL") {
            contentType(ContentType.Application.Json)
            setBody(User(name, age))
        }
        val id : Int = httpResponse.body()
        Log.d("KtorClient", "Send data to server. User id: $id")
    }
    suspend fun userList(resultLD : MutableLiveData<String>) {
        resultLD.value = client.get("$connectionURL$usersSubURL$allSubURL").body<List<User>>().joinToString("\n")
    }

    suspend fun getUser(id: Int, resultLD: MutableLiveData<String>) {
        val user: User = client.get("$connectionURL$usersSubURL/${id}").body()
        resultLD.value =  "getString(R.string.found, user.id.toString(), user.name, user.age.toString()) }"
    }
}