package com.salinas.seriessalinas.details

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.salinas.seriessalinas.BaseURL
import org.json.JSONException

class DetailsModel : DetailsContract.Model {
    val baseUrl: String = BaseURL().baseURL
    val apiKey: String = BaseURL().apiKey

    override fun downloadVideos(
        successListener: (MutableList<String>) -> Unit,
        errorListener: (String) -> Unit, context: Context, id_movie: String
    ) {

        getVideos(successListener, errorListener, context, id_movie)

    }


    fun getVideos(
        successListener: (MutableList<String>) -> Unit,
        errorListener: (String) -> Unit, context: Context, id_movie: String
    ) {


        val url = baseUrl + id_movie + "/videos?api_key=" + apiKey + "&language=en-US&page=1"


        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("results")

                var videoList = mutableListOf<String>()

                for (i in 0 until jsonArray.length()) {


                    var urlVideo =
                        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + jsonArray.getJSONObject(
                            i
                        )
                            .optString("key") + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"


                    videoList.add(urlVideo)

                }

                successListener(videoList)


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