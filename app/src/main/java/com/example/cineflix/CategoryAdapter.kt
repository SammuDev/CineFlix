package com.example.cineflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitleCategory: TextView = itemView.findViewById(R.id.text_title)
        private val recyclerViewCategory: RecyclerView =
            itemView.findViewById(R.id.recyclerView_category)

        fun bind(category: Category) {
            textTitleCategory.text = category.theme

            recyclerViewCategory.adapter =
                MovieAdapter(category.movies, R.layout.movie_item, onItemClickListener)
            recyclerViewCategory.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        }
    }
}
