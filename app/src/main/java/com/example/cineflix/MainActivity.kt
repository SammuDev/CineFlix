package com.example.cineflix

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.model.Category
import com.example.cineflix.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.Callback {
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar_mainActivity)

        adapter = CategoryAdapter(categories)
        recyclerView = findViewById(R.id.recyclerView_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=d101cf9b-c1d7-4556-957f-71df3a8d0758")
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)

        adapter.notifyDataSetChanged()

        progressBar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
    }
}
