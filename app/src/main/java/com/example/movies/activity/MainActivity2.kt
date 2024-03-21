package com.example.movies.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movies.data.CategoryMovie
import com.example.movies.data.New_item
import com.example.movies.databinding.ActivityMain2Binding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var movie: New_item
    private var id: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        id=intent.getStringExtra("Id").toString()


        findMovieById(id!!)
    }

    private fun findMovieById(id: String) {


        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CategoryMovie = retrofit.create(CategoryMovie::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.findById(id, "5a61c33")


            runOnUiThread {
                movie = response.body()!!
                initUI2()

            }
        }
    }

    private fun initUI2() {
        binding.textMovie.text=movie.title
        Picasso.get().load(movie.poster).into(binding.imageMovie)
        binding.year.text=movie.year
        binding.duration.text=movie.runtime
        binding.synopsis.text=movie.plot
        binding.genre.text=movie.genre
        binding.director.text=movie.director
        binding.country.text=movie.country

    }
}