package com.example.android.todotask

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.Integer.parseInt

class ImageAdapter(var context: Context?, var imageList: ArrayList<String>?):BaseAdapter() {
    override fun getCount(): Int {
       return imageList!!.size
    }

    override fun getItem(p0: Int): Any? {
       return null
    }

    override fun getItemId(p0: Int): Long {
       return 0
    }

    @OptIn(InternalCoroutinesApi::class)
    @SuppressLint("ViewHolder")
    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item,null)
        var image = view.findViewById<ImageView>(R.id.icon)
 //       image.setImageURI(imageList[i])
  //      image.setImageBitmap(imageList[i])
        Glide.with(context!!).load(imageList!![i]).into(image)
        return view
    }

}