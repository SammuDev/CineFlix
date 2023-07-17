package com.example.cineflix

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Movie
import com.example.cineflix.model.MovieDetail
import com.example.cineflix.util.MovieTask

class MovieActivity : AppCompatActivity(), MovieTask.Callback {
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieCast: TextView
    private lateinit var recyclerMovies: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var progressBar: ProgressBar

    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieTitle = findViewById(R.id.movie_title)
        movieDescription = findViewById(R.id.movie_description)
        movieCast = findViewById(R.id.movie_cast)
        recyclerMovies = findViewById(R.id.recyclerView_similarMovies)
        progressBar = findViewById(R.id.movie_progress)

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalStateException("ID não encontrado!")
        val url =
            "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=d101cf9b-c1d7-4556-957f-71df3a8d0758"

        MovieTask(this).execute(url)

        movieAdapter = MovieAdapter(movies, R.layout.movie_similar_item)
        recyclerMovies.adapter = movieAdapter
        recyclerMovies.layoutManager = GridLayoutManager(this, 3)

        val toolbar: Toolbar = findViewById(R.id.toolbar_movie)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.rollback_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        val layerDrawable: LayerDrawable =
            ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable
        val imageDrawable = ContextCompat.getDrawable(this, R.drawable.game_of_thrones)
        layerDrawable.setDrawableByLayerId(R.id.cover_drawable, imageDrawable)

        val imageCover: ImageView = findViewById(R.id.image_movie)
        imageCover.setImageDrawable(layerDrawable)
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onFailure(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@MovieActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResult(movieDetail: MovieDetail) {
        progressBar.visibility = View.GONE

        movieTitle.text = movieDetail.movie.title
        movieDescription.text = movieDetail.movie.description
        movieCast.text = getString(R.string.cast, movieDetail.movie.cast)

        movies.clear()
        movies.addAll(movieDetail.similar)
        movieAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
