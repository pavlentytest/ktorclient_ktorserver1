package com.example.myapplication.model

@kotlinx.serialization.Serializable
data class User(
    val name: String?,
    val age: Int?
)