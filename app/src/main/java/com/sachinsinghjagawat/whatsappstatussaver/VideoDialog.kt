package com.sachinsinghjagawat.whatsappstatussaver

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.apache.commons.io.FileUtils
import java.io.*


class VideoDialog (var path :String, context: Context) : BottomSheetDialogFragment() {
    var contex = context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
        if (path.endsWith("mp4"))
        return inflater.inflate(R.layout.video_view, container, false)
        else return inflater.inflate(R.layout.image_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (path.endsWith("mp4")){
            var v=  view.findViewById<VideoView>(R.id.videoView)
            var uri = Uri.fromFile(File(path));
            v.setVideoURI(uri);
            v.start()
        }else {
            var v= view.findViewById<ImageView>(R.id.imageView);
            var uri = Uri.fromFile(File(path));
            Glide.with(contex).load(uri).into(v)
        }
        view.findViewById<FloatingActionButton>(R.id.share_button).setOnClickListener(View.OnClickListener {
            share(path)
        })
        view.findViewById<FloatingActionButton>(R.id.download_button).setOnClickListener(View.OnClickListener {
            save(path)
        })
    }

    fun share (path : String){
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
            if (path.endsWith("jpg"))
            type = "image/jpg"
            else type= "video/mp4"
        }
        startActivity(Intent.createChooser(shareIntent, "Share image"))
    }
    fun save (path :String){
        var rootpath = Environment.getExternalStorageDirectory().absolutePath;
        rootpath += "/Download/MyStorySaver/WhatsApp"
        if (!File(rootpath).exists()){
            File(rootpath).mkdirs()
        }
        try {
            FileUtils.copyFileToDirectory(File(path), File(rootpath))
            Toast.makeText(contex, "File Saved Successfull :)" , Toast.LENGTH_SHORT).show()
            MainActivity2.updatelist3()
        }catch (e: IOException){
            e.printStackTrace()
            Toast.makeText(contex, "File Not Saved :(" , Toast.LENGTH_SHORT).show()
        }
    }
}