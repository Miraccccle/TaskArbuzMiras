package com.example.taskarbuzmiras.domain.models

data class UnsplashPhoto(
    val id: String,
    val urls: Urls,
    val description: String?,
    val alt_description: String?
)
data class Urls(
    val regular: String
)
