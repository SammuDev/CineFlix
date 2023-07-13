package com.example.cineflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Movie
import com.squareup.picasso.Picasso

// LISTA HORIZONTAL
class MovieAdapter(
    private val moviesList: List<Movie>, @LayoutRes private val layoutId: Int
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val imageBackground: ImageView = itemView.findViewById(R.id.image_background)
            Picasso.get().load(movie.coverUrl).into(imageBackground)
        }
    }
}
