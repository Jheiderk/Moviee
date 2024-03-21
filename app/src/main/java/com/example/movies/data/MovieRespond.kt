package com.example.movies.data

import com.google.gson.annotations.SerializedName

data class MovieRespond (

    @SerializedName("Search") val results: List<New_item>, // Cambiado de "New_item" a "Search"
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String
    ) {

    }

    class New_item (

        @SerializedName("imdbID") val imdbId:String,
        @SerializedName("Title") val title:String,
        @SerializedName("Year") val year:String,
        @SerializedName("Poster") val poster:String,
        @SerializedName("Plot") val plot:String,
        @SerializedName("Runtime") val runtime:String,
        @SerializedName("Director") val director:String,
        @SerializedName("Genre") val genre:String,
        @SerializedName("Country") val country:String

    )
    {
}