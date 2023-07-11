package com.example.cineflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val toolbar: Toolbar = findViewById(R.id.toolbar_movie)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.rollback_arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
