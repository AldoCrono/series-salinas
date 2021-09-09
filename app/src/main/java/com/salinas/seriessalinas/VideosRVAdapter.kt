package com.salinas.seriessalinas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView


class VideosRVAdapter(
    private val context: Context,
    urlList: MutableList<String>
) : RecyclerView.Adapter<VideosRVAdapter.MyViewHolder>() {
    private val urlList: MutableList<String> = urlList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.root.setOnClickListener { }

        holder.wv_video.clearCache(false)

        val webSettings: WebSettings = holder.wv_video.getSettings()
        webSettings.javaScriptEnabled = true
        holder.wv_video.setWebViewClient(WebViewClient())

        holder.wv_video.loadData(urlList[position], "text/html", "utf-8")


    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    inner class MyViewHolder(var root: View) :
        RecyclerView.ViewHolder(root) {
        var wv_video: WebView = root.findViewById(R.id.wv_video)

    }


}