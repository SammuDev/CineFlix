package com.example.cineflix

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
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
    private val movieTitle: TextView = findViewById(R.id.movie_title)
    private val movieDescription: TextView = findViewById(R.id.movie_description)
    private val movieCast: TextView = findViewById(R.id.movie_cast)
    private val recyclerMovies: RecyclerView = findViewById(R.id.recyclerView_similarMovies)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalStateException("ID não encontrado!")
        val url =
            "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=d101cf9b-c1d7-4556-957f-71df3a8d0758"

        MovieTask(this).execute(url)

        movieTitle.text = "Game Of Thrones"
        movieDescription.text =
            "Situada nos continentes fictícios de Westeros e Essos, a série centra-se no Trono de Ferro dos Sete Reinos e segue um enredo de alianças e conflitos entre as famílias nobres dinásticas, seja competindo para reivindicar o trono ou lutando por sua independência."
        movieCast.text = getString(
            R.string.cast,
            "Nikolaj Coster-Waldau, Michelle Fairley, Lena Headey, Emilia Clarke, Iain Glen, Harry Lloyd, Kit Harington, Sophie Turner, Maisie Williams, Richard Madden, Alfie Allen, Isaac Hempstead Wright, Jack Gleeson, Rory McCann, Peter Dinklage, Jason Momoa, Aidan Gillen, Liam Cunningham, John Bradley, Stephen Dillane, Carice van Houten\tMelisandre\t, James Cosmo, Jerome Flynn, Conleth Hill, Sibel Kekilli, Natalie Dormer, Charles Dance, Oona Chaplin, Rose Leslie, Joe Dempsie\tGendry, Kristofer Hivju, Gwendoline Christie, Iwan Rheon, Hannah Murray, Michiel Huisman, Nathalie Emmanuel, Indira Varma, Dean-Charles Chapman, Tom Wlaschiha[e], Michael McElhatton, Jonathan Pryce, Jacob Anderson"
        )

        val movies = mutableListOf<Movie>()

        recyclerMovies.adapter = MovieAdapter(movies, R.layout.movie_similar_item)
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
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        Toast.makeText(this@MovieActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResult(movieDetail: MovieDetail) {
        Log.i("teste", movieDescription.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
