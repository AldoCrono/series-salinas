package com.salinas.seriessalinas

data class Movie(
    var poster_path: String,
    var overview: String,
    var release_date: String,
    var id: Int,
    var original_title: String,
    var original_language: String,
    var title: String,
    var backdrop_path: String,
    var video: Boolean,
    var vote_average: Double,
    var total_pages: Int
)