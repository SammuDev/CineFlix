package com.example.cineflix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Category
import com.example.cineflix.model.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = mutableListOf<Category>()
        for(i in 0 until 5) {
            val movies = mutableListOf<Movie>()
            for(j in 0 until 15) {
                val newMovie = Movie(R.drawable.movie_4)
                movies.add(newMovie)
            }

            val newCategory = Category("Category - ${i + 1}", movies)
            categories.add(newCategory)
        }

        val adapter = CategoryAdapter(categories)
        recyclerView = findViewById(R.id.recyclerView_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
