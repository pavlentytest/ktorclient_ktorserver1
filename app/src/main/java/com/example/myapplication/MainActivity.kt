package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

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
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.url.text = connectionURL

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.send.setOnClickListener {
            with(binding) {
                mainViewModel.addUser(name.text.toString(), age.text.toString().toInt())
            }
        }
        binding.receive.setOnClickListener {
            mainViewModel.listUsers()
        }
        binding.get.setOnClickListener {
            mainViewModel.getUser(3)

        }
    }
}