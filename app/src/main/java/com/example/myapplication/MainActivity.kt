package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.example.myapplication.databinding.ActivityMainBinding
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val age: Int) {
    var id: Int = 0
    constructor(id_: Int, name: String, age: Int) : this(name, age) {
        this.id = id_
    }
    override fun toString(): String = "$id - $name - $age"
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val connectionURL: String = "http://192.168.86.38:8080"
    private val scope = CoroutineScope(Dispatchers.IO)
    private val client = HttpClient{
        install(ContentNegotiation) {
            json()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.url.text = connectionURL
        binding.send.setOnClickListener {
            scope.launch{ addUser("/users") }
        }
        binding.receive.setOnClickListener {
            scope.launch{ userList("/users/all") }
        }
        binding.get.setOnClickListener {
            scope.launch { getUser(3,"/users/") }
        }
    }
    private suspend fun addUser(sub: String) {
        val httpResponse = client.post("$connectionURL$sub") {
            contentType(ContentType.Application.Json)
            setBody(User(binding.name.text.toString(), binding.age.text.toString().toInt()))
        }
        val id : Int = httpResponse.body()
        Log.d("KtorClient", "Send data to server. User id: $id")
    }
    private suspend fun userList(sub: String) {
        withContext(Dispatchers.Main) {
            binding.result.text = client.get("$connectionURL$sub").body<List<User>>().joinToString("\n")
        }
    }
    private suspend fun getUser(id: Int, sub: String) {
        val user: User = client.get("$connectionURL$sub${id}").body()
        withContext(Dispatchers.Main) {
            binding.result.text = getString(R.string.found, user.id.toString(), user.name, user.age.toString())
        }
    }
}