package com.example.movies.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.New_item
import com.example.movies.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter (private var items:List<New_item> = listOf(), val onClickListener: (position:Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)


    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = items[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }


    }
    fun updateItems(results: List<New_item>?) = if (results != null) {
        items = results
        notifyDataSetChanged()
    }else {
        Log.i("HTTP", "es nulo")
    }
}

class ViewHolder(val binding:ItemMovieBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(listMovie: New_item) {
        binding.textMovie.text = listMovie.title
        Picasso.get().load(listMovie.poster).into(binding.imageMovie)

        binding.titleText.text=listMovie.title

        binding.typeMovie.text= listMovie.type
        binding.yearMovie.text=listMovie.year

    }
}