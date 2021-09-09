package com.salinas.seriessalinas.details

import android.content.Context

interface DetailsContract {

    interface View {
        fun showVideos(videoList: MutableList<String>)
        fun showError(message: String)
    }

    interface Presenter {
        fun getVideos(context: Context, id_movie: String)
    }

    interface Model {
        fun downloadVideos(
            successListener: (MutableList<String>) -> Unit,
            errorListener: (String) -> Unit, context: Context, id_movie: String
        )
    }


}