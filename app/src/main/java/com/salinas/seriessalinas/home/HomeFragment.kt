package com.salinas.peliculas.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salinas.seriessalinas.Movie
import com.salinas.seriessalinas.MoviesRVAdapter
import com.salinas.seriessalinas.R
import com.salinas.seriessalinas.Util;
import com.salinas.seriessalinas.home.HomeContract
import com.salinas.seriessalinas.home.HomeModel
import com.salinas.seriessalinas.home.HomePresenter

class HomeFragment : Fragment(),
    HomeContract.View {

    var ll_botones: LinearLayout? = null
    var rl_playing: RelativeLayout? = null
    var rl_popular: RelativeLayout? = null
    var lb_playing: TextView? = null
    var lb_popular: TextView? = null
    var linea_playing: View? = null
    var linea_popular: View? = null
    var rv_movies: RecyclerView? = null
    var leftArrow: ImageView? = null;
    var rightArrow: ImageView? = null;
    var lb_pages: TextView? = null;
    var playing: Boolean = true;
    var page: Int = 1
    var total_pages: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedIntanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false);

        ll_botones = root.findViewById<LinearLayout>(R.id.ll_botones)
        rl_playing = root.findViewById<RelativeLayout>(R.id.rl_playing)
        rl_popular = root.findViewById<RelativeLayout>(R.id.rl_popular)
        lb_playing = root.findViewById<TextView>(R.id.lb_playing)
        lb_popular = root.findViewById<TextView>(R.id.lb_popular)
        linea_playing = root.findViewById<View>(R.id.linea_playing)
        linea_popular = root.findViewById<View>(R.id.linea_popular)
        rv_movies = root.findViewById<RecyclerView>(R.id.rv_movies)
        leftArrow = root.findViewById<ImageView>(R.id.leftArrow)
        rightArrow = root.findViewById<ImageView>(R.id.rightArrow)
        lb_pages = root.findViewById<TextView>(R.id.lb_pages)


        val homePresenter = HomePresenter(this, HomeModel())
        homePresenter.getPlayingNow(requireContext(), page)
        linea_playing?.visibility = View.VISIBLE
        linea_popular?.visibility = View.INVISIBLE

        rl_playing?.setOnClickListener {

            if (!playing) {
                page = 1
                homePresenter.getPlayingNow(requireContext(), page)
                linea_playing?.visibility = View.VISIBLE
                linea_popular?.visibility = View.INVISIBLE
                playing = true

            }
        }

        rl_popular?.setOnClickListener {

            if (playing) {
                page = 1
                homePresenter.getMostPopular(requireContext(), page)
                linea_playing?.visibility = View.INVISIBLE
                linea_popular?.visibility = View.VISIBLE
                playing = false

            }
        }

        leftArrow?.setOnClickListener {

            if (page > 1) {
                page--;
                if (playing) {
                    homePresenter.getPlayingNow(requireContext(), page)
                    lb_pages?.setText("Page " + page + " of " + total_pages)
                } else {
                    homePresenter.getMostPopular(requireContext(), page)
                    lb_pages?.setText("Page " + page + " of " + total_pages)
                }
            }

        }

        rightArrow?.setOnClickListener {

            if (page < total_pages) {
                page++;
                if (playing) {
                    homePresenter.getPlayingNow(requireContext(), page)
                    lb_pages?.setText("Page " + page + " of " + total_pages)
                } else {
                    homePresenter.getMostPopular(requireContext(), page)
                    lb_pages?.setText("Page " + page + " of " + total_pages)
                }
            }

        }


        return root;
    }

    override fun showPlayingNow(movieList: MutableList<Movie>) {
        if (Util.isActivityAlive(activity as Activity)) {
            val myadapter = MoviesRVAdapter(requireActivity(), movieList)
            rv_movies?.setLayoutManager(LinearLayoutManager(context))
            rv_movies?.setAdapter(myadapter)
            total_pages = movieList.get(0).total_pages;
            lb_pages?.setText("Page " + page + " of " + total_pages)
        }

    }

    override fun showMostPopular(movieList: MutableList<Movie>) {
        if (Util.isActivityAlive(activity as Activity)) {
            val myadapter = MoviesRVAdapter(requireActivity(), movieList)
            rv_movies?.setLayoutManager(LinearLayoutManager(context))
            rv_movies?.setAdapter(myadapter)
            total_pages = movieList.get(0).total_pages;
            lb_pages?.setText("Page " + page + " of " + total_pages)
        }

    }

    override fun showError(message: String) {
        if (Util.isActivityAlive(activity as Activity)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (page > 1) {
                page--;
                lb_pages?.setText("Page " + page + " of " + total_pages)
            }
        }
    }
}