package com.example.android.postszemoga.data.remote.dto

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)