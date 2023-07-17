package com.example.cineflix.model

data class MovieDetail(
    val movie: Movie,
    val similar: List<Movie>
)
