package com.salinas.seriessalinas.home

import android.content.Context
import com.salinas.seriessalinas.Movie

interface HomeContract {

    interface View {
        fun showPlayingNow(movieList: MutableList<Movie>)
        fun showMostPopular(movieList: MutableList<Movie>)
        fun showError(message: String)
    }

    interface Presenter {
        fun getPlayingNow(context: Context, page: Int)
        fun getMostPopular(context: Context, page: Int)
    }

    interface Model {
        fun downloadPlayingNow(
            successListener: (MutableList<Movie>) -> Unit,
            errorListener: (String) -> Unit, context: Context, page: Int
        )

        fun downloadMostPopular(
            successListener: (MutableList<Movie>) -> Unit,
            errorListener: (String) -> Unit, context: Context, page: Int
        )
    }


}