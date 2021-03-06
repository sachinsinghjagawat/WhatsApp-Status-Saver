package com.sachinsinghjagawat.whatsappstatussaver

import android.os.Bundle
import android.os.Environment
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.sachinsinghjagawat.whatsappstatussaver.ui.main.SectionsPagerAdapter
import com.sachinsinghjagawat.whatsappstatussaver.databinding.ActivityMain2Binding
import java.io.File

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    val OLD_INITIAL_PATH= "/WhatsApp/Media/.Statuses"
    val NEW_INITIAL_PATH= "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses/"

    companion object {
        var list1 = arrayListOf<String>();
        var list2 = arrayListOf<String>();

        var list3 = arrayListOf<String>();
        fun updatelist3 (){
            list3.clear()
            resetlist3();
        }

        private fun resetlist3() {
            var rootpath = Environment.getExternalStorageDirectory().absolutePath;
            rootpath += "/Download/MyStorySaver/WhatsApp/"
            File (rootpath).walk().forEach {
                var path: String = it.toString();
                if(path.endsWith("jpg") || path.endsWith("mp4"))list3.add(it.toString());
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMain2Binding.inflate(layoutInflater)
     setContentView(binding.root)

        setfragment(1)
        setfragment(2)
        setfragment(3)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    override fun onResume() {
        super.onResume()
        list1.clear()
        list2.clear()
        list3.clear()

        setfragment(1)
        setfragment(2)
        setfragment(3)
    }

    fun setfragment (tabno:Int){
        var rootpath = Environment.getExternalStorageDirectory().absolutePath;
        if (tabno==3){
            execute(rootpath + "/Download/MyStorySaver/WhatsApp/", tabno)
            return;
        }
        if (File(rootpath+ OLD_INITIAL_PATH).exists()) {
            execute(rootpath + OLD_INITIAL_PATH, tabno);
        } else if (File(rootpath+ NEW_INITIAL_PATH).exists()) {
            execute(rootpath + NEW_INITIAL_PATH, tabno);
        }
    }

    fun execute (pathname: String, tabno: Int){
        File (pathname).walk().forEach {
            var path: String = it.toString();
//            Log.i("Sachin Bhai= ", path);
            if (tabno==1){
                if(path.endsWith("jpg")) list1.add(it.toString());
            }else if (tabno==2) {
                if(path.endsWith("mp4")) list2.add(it.toString());
            }else if (tabno==3){
                if(path.endsWith("jpg") || path.endsWith("mp4"))list3.add(it.toString());
            }
        }
//        var fl : Array<out File>? = File (pathname).listFiles();
//        if (fl != null) {
//            fl.sortByDescending {
//                it.lastModified()
//            }
//        }
//        if (fl != null) {
//            fl.forEach {
//                var path: String = it.toString();
//    //            Log.i("Sachin Bhai= ", path);
//                if (tabno==1){
//                    if(path.endsWith("jpg")) list1.add(it.toString());
//                }else if (tabno==2) {
//                    if(path.endsWith("mp4")) list2.add(it.toString());
//                }else if (tabno==3){
//                    if(path.endsWith("jpg") || path.endsWith("mp4"))list3.add(it.toString());
//                }
//            }
//        }
    }
    fun  getlist1 (): ArrayList <String> {
        return list1
    }
}