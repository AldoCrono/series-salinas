package com.salinas.seriessalinas.home

import android.content.Context

class HomePresenter(
    private val homeView: HomeContract.View,
    private val homeModel: HomeContract.Model
) : HomeContract.Presenter {

    override fun getPlayingNow(context: Context, page: Int) {
        homeModel.downloadPlayingNow({ movieList ->
            homeView.showPlayingNow(movieList)
        }, { errorMessage ->
            homeView.showError(errorMessage)
        }, context, page)
    }

    override fun getMostPopular(context: Context, page: Int) {
        homeModel.downloadMostPopular({ movieList ->
            homeView.showMostPopular(movieList)
        }, { errorMessage ->
            homeView.showError(errorMessage)
        }, context, page)
    }
}


