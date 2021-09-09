package com.salinas.seriessalinas.home

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.salinas.seriessalinas.BaseURL
import com.salinas.seriessalinas.Movie
import org.json.JSONException

class HomeModel : HomeContract.Model {
    val baseUrl: String = BaseURL().baseURL
    val apiKey: String = BaseURL().apiKey

    override fun downloadPlayingNow(
        successListener: (MutableList<Movie>) -> Unit,
        errorListener: (String) -> Unit, context: Context, page: Int
    ) {

        getPlayingNow(1, successListener, errorListener, context, page)

    }

    override fun downloadMostPopular(
        successListener: (MutableList<Movie>) -> Unit,
        errorListener: (String) -> Unit, context: Context, page: Int
    ) {
        val movieList = mutableListOf<Movie>()

        getPlayingNow(2, successListener, errorListener, context, page)

    }

    fun getPlayingNow(
        category: Int, successListener: (MutableList<Movie>) -> Unit,
        errorListener: (String) -> Unit, context: Context, page: Int
    ) {

        var url: String

        if (category == 1) {
            url = baseUrl + "now_playing?api_key=" + apiKey + "&language=en-US&page=" + page
        } else {
            url = baseUrl + "popular?api_key=" + apiKey + "&language=en-US&page=1" + page
        }

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("results")

                var movieList = mutableListOf<Movie>()

                for (i in 0 until jsonArray.length()) {

                    var oMovie = Movie(
                        "", "", "",
                        0, "", "", "", "", false,
                        0.0, 1
                    )

                    oMovie.id = jsonArray.getJSONObject(i).optInt("id")
                    oMovie.poster_path = jsonArray.getJSONObject(i).optString("poster_path")
                    oMovie.overview = jsonArray.getJSONObject(i).optString("overview")
                    oMovie.release_date = jsonArray.getJSONObject(i).optString("release_date")
                    oMovie.original_title = jsonArray.getJSONObject(i).optString("original_title")
                    oMovie.title = jsonArray.getJSONObject(i).optString("title")
                    oMovie.vote_average = jsonArray.getJSONObject(i).optDouble("vote_average")
                    oMovie.backdrop_path = jsonArray.getJSONObject(i).optString("backdrop_path")
                    oMovie.video = jsonArray.getJSONObject(i).optBoolean("video")
                    oMovie.total_pages = response.optInt("total_pages")
                    movieList.add(oMovie)

                }

                successListener(movieList)

            } catch (e: JSONException) {
                e.printStackTrace()

                errorListener("Catch error: $e")


            }
        },
            { error ->
                if (error is TimeoutError || error is NoConnectionError) { //This indicates that the reuest has either time out or there is no connection

                    errorListener("No Connection: Please verify your internet connection.")

                } else {
                    errorListener("Volley error: $error")

                }
            })
        request.retryPolicy = DefaultRetryPolicy(
            15000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        Volley.newRequestQueue(context).add(request)
    }
}