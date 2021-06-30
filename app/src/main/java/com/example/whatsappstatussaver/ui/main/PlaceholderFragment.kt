package com.example.whatsappstatussaver.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.whatsappstatussaver.MainActivity2
import com.example.whatsappstatussaver.R
import java.io.File


class PlaceholderFragment : Fragment() {

    val NUM_OF_COLUMNS = 2;
//    var list = arrayListOf<String>();

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_container);
        var tabno= arguments?.getInt(ARG_SECTION_NUMBER)?:1;
        var list : ArrayList<String>
        if (tabno==1) list= MainActivity2.list1
        else if (tabno==2) list= MainActivity2.list2
        else if (tabno==3) list= MainActivity2.list3
        else list= MainActivity2.list1

        var st= StatusAdapter(list, requireContext());
        var sglm= StaggeredGridLayoutManager (NUM_OF_COLUMNS, LinearLayoutManager.VERTICAL)
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= sglm
        recyclerView.adapter= st;
    }



    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
    class StatusAdapter (var list: ArrayList<String>, var context: Context) : RecyclerView.Adapter <StatusAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var path = list[position]
            var rq = RequestOptions ().placeholder(R.drawable.sample_image);
            var uri = Uri.fromFile(File(path));
            var w= holder.imageView.width
//            val ratio: Float = item.getHeight() / item.getWidth()
//            holder.imageView.minimumHeight= (ratio * w).toInt();
            Glide.with(context).load(uri).apply(rq)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .crossFade()
                .into(holder.imageView)
//            val set = ConstraintSet()
//            val ratio =String.format("%d:%d", uri.width,poster.height)
//            set.clone(holder.mConstraintLayout)
//            set.setDimensionRatio(holder.mImgPoster.id, ratio)
//            set.applyTo(holder.mConstraintLayout)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun share (path : String){
            val shareIntent = Intent(Intent.ACTION_SEND)

            shareIntent.type = "image/jpg"
            shareIntent.putExtra(
                Intent.EXTRA_STREAM,
                Uri.parse("file://" + path)
            )
            context.startActivity(Intent.createChooser(shareIntent, "Share image"))
        }

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imageView= itemView.findViewById<ImageView>(R.id.image_container)
        }
    }
}