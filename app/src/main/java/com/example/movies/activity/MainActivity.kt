package com.example.movies.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MovieAdapter
import com.example.movies.data.CategoryMovie
import com.example.movies.data.New_item
import com.example.movies.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var listMovie: List<New_item>
    private lateinit var movie: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND ||
                (keyEvent?.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {

                movie = textView.text.toString()

                if (movie.isNotEmpty()) { // Verifica si la lista no está vacía antes de procesarla
                    searchMovie(movie)
                    Toast.makeText(this, R.string.search, Toast.LENGTH_SHORT).show()
                    binding.editText.text.clear()
                    binding.editText.clearFocus()
                    hideKeyboard()
                }

                true
            } else {
                // Devolver false indica que el evento no ha sido manejado
                false
            }
        }




        adapter = MovieAdapter {
            adapterUI(it)
        }

        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)

    }

    private fun adapterUI(position:Int){


        val movie:New_item =listMovie[position]

        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("Id", movie.imdbId)
        intent.putExtra("name", movie.title)
        intent.putExtra("image", movie.poster)
        startActivity(intent)
    }



    private fun searchMovie(query: String) {
        var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        val service: CategoryMovie = retrofit.create(CategoryMovie::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.searchByName(query, "5a61c33")


            runOnUiThread {
                listMovie = response.body()?.results!!
                adapter.updateItems(response.body()?.results)

            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(binding.editText.windowToken, 0)
    }


}