package com.salinas.seriessalinas.details

import android.content.Context

class DetailPresenter(
    private val homeView: DetailsContract.View,
    private val homeModel: DetailsContract.Model
) : DetailsContract.Presenter {
    override fun getVideos(context: Context, id_movie: String) {
        homeModel.downloadVideos({ videoList ->
            homeView.showVideos(videoList)
        }, { errorMessage ->
            homeView.showError(errorMessage)
        }, context, id_movie)
    }


}


