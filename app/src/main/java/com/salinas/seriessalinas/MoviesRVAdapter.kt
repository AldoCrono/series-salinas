package com.salinas.seriessalinas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salinas.peliculas.home.DetailsFragment


class MoviesRVAdapter(
    private val context: Context,
    movieList: MutableList<Movie>
) : RecyclerView.Adapter<MoviesRVAdapter.MyViewHolder>() {

    val imageBaseURL: String = BaseURL().imageBaseURL

    private val movieList: MutableList<Movie>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View

        view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.root.setOnClickListener { }
        holder.lb_title.setText(movieList[position].title)
        holder.lb_average.setText((movieList[position].vote_average).toString())
        holder.lb_release.setText("Release date: " + movieList[position].release_date)


        holder.rating_bar.rating = (movieList[position].vote_average.toFloat()) / 2

        Glide.with(context).load(imageBaseURL + movieList[position].poster_path).into(holder.poster)

        holder.rl_movie.setOnClickListener {

            val datosAEnviar = Bundle()
            datosAEnviar.putInt("id", movieList[position].id)
            datosAEnviar.putString("title", movieList[position].title)
            datosAEnviar.putString("overview", movieList[position].overview)
            datosAEnviar.putString("vote_average", movieList[position].vote_average.toString())
            datosAEnviar.putFloat("rating", (movieList[position].vote_average.toFloat()) / 2)
            datosAEnviar.putString("backdrop", movieList[position].backdrop_path)
            datosAEnviar.putBoolean("video", movieList[position].video)

            val selectedFragment: Fragment = DetailsFragment()
            selectedFragment.arguments = datosAEnviar
            val fragmentManager: FragmentManager =
                (context as MainActivity).getSupportFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragmentContainer, selectedFragment)
                .addToBackStack("stack").commit()

        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MyViewHolder(var root: View) :
        RecyclerView.ViewHolder(root) {
        var lb_title: TextView
        var rating_bar: RatingBar
        var lb_average: TextView
        var lb_release: TextView
        var rl_movie: RelativeLayout;
        var poster: ImageView;

        init {
            lb_title = root.findViewById(R.id.lb_title)
            rating_bar = root.findViewById(R.id.rating_bar)
            lb_average = root.findViewById(R.id.lb_average)
            lb_release = root.findViewById(R.id.lb_release)
            rl_movie = root.findViewById(R.id.rl_movie)
            poster = root.findViewById(R.id.poster)
        }
    }


    init {
        this.movieList = movieList
    }
}