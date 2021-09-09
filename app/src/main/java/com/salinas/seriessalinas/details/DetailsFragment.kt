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
import com.bumptech.glide.Glide
import com.salinas.seriessalinas.*
import com.salinas.seriessalinas.details.DetailPresenter
import com.salinas.seriessalinas.details.DetailsContract
import com.salinas.seriessalinas.details.DetailsModel

class DetailsFragment : Fragment(), DetailsContract.View {

    var bundle: Bundle? = null
    val imageBaseURL: String = BaseURL().imageBaseURL
    var rv_videos: RecyclerView? = null
    var lb_videos: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedIntanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_details, container, false);

        val backdrop = root.findViewById<ImageView>(R.id.backdrop)
        val lb_title = root.findViewById<TextView>(R.id.lb_title)
        val rating_bar = root.findViewById<RatingBar>(R.id.rating_bar)
        val lb_average = root.findViewById<TextView>(R.id.lb_average)
        val lb_overview = root.findViewById<TextView>(R.id.lb_overview)
        val leftArrow = root.findViewById<ImageView>(R.id.leftArrow)
        lb_videos = root.findViewById<TextView>(R.id.lb_videos)
        rv_videos = root.findViewById<RecyclerView>(R.id.rv_videos)

        bundle = arguments

        lb_title.setText(bundle?.getString("title"))
        lb_average.setText(bundle?.getString("vote_average"))
        lb_overview.setText(bundle?.getString("overview"))
        rating_bar.rating = bundle?.getFloat("rating")!!

        Glide.with(requireContext()).load(imageBaseURL + bundle?.getString("backdrop"))
            .into(backdrop)

        val detailPresenter = DetailPresenter(this, DetailsModel())
        detailPresenter.getVideos(requireContext(), bundle?.getInt("id").toString())

        leftArrow.setOnClickListener {
            activity?.onBackPressed()
        }


        return root;
    }

    override fun showVideos(videoList: MutableList<String>) {
        if (Util.isActivityAlive(activity as Activity)) {
            val myadapter = VideosRVAdapter(requireActivity(), videoList)
            rv_videos?.setLayoutManager(LinearLayoutManager(context))
            rv_videos?.setAdapter(myadapter)
            if (videoList.size == 0) {
                lb_videos?.setText("No videos available")
            } else {
                lb_videos?.setText("Videos")
            }
        }
    }


    override fun showError(message: String) {
        if (Util.isActivityAlive(activity as Activity)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}