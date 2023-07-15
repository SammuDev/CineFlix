package com.example.cineflix

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Movie

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieTitle: TextView = findViewById(R.id.movie_title)
        val movieDescription: TextView = findViewById(R.id.movie_description)
        val movieCast: TextView = findViewById(R.id.movie_cast)
        val recyclerMovies: RecyclerView = findViewById(R.id.recyclerView_similarMovies)

        movieTitle.text = "Game Of Thrones"
        movieDescription.text = "Situada nos continentes fictícios de Westeros e Essos, a série centra-se no Trono de Ferro dos Sete Reinos e segue um enredo de alianças e conflitos entre as famílias nobres dinásticas, seja competindo para reivindicar o trono ou lutando por sua independência."
        movieCast.text = getString(R.string.cast, "Nikolaj Coster-Waldau, Michelle Fairley, Lena Headey, Emilia Clarke, Iain Glen, Harry Lloyd, Kit Harington, Sophie Turner, Maisie Williams, Richard Madden, Alfie Allen, Isaac Hempstead Wright, Jack Gleeson, Rory McCann, Peter Dinklage, Jason Momoa, Aidan Gillen, Liam Cunningham, John Bradley, Stephen Dillane, Carice van Houten\tMelisandre\t, James Cosmo, Jerome Flynn, Conleth Hill, Sibel Kekilli, Natalie Dormer, Charles Dance, Oona Chaplin, Rose Leslie, Joe Dempsie\tGendry, Kristofer Hivju, Gwendoline Christie, Iwan Rheon, Hannah Murray, Michiel Huisman, Nathalie Emmanuel, Indira Varma, Dean-Charles Chapman, Tom Wlaschiha[e], Michael McElhatton, Jonathan Pryce, Jacob Anderson")

        val movies = mutableListOf<Movie>()
//        for(i in 0 until 15) {
//            val newMovie = Movie(R.drawable.movie_4)
//            movies.add(newMovie)
//        }
//
//        recyclerMovies.adapter = MovieAdapter(movies, R.layout.movie_similar_item)
//        recyclerMovies.layoutManager = GridLayoutManager(this, 3)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
