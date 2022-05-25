package com.example.android.todotask

import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.todotask.models.ImageData
import com.example.android.todotask.viewModels.ImageViewModel
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File
import java.io.FileOutputStream


class ImageFragment() : Fragment(R.layout.fragment_image) {
    lateinit var imageViewModel1: ImageViewModel
    val REQUEST_CODE = 100
    val filename= "myfile"
    var uri: Uri? = null
    var imageAdapter:ImageAdapter = ImageAdapter(null,null)
    var images = ArrayList<String>()

    @SuppressLint("LogConditional")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (requireActivity().application as TODOApplication).userRepository
        imageViewModel1 = ViewModelProvider(
            this,
            ImageViewModelFactory(repository)
        ).get(ImageViewModel::class.java)

        add_image.setOnClickListener {
            getImage()
        }

        imageViewModel1.getImage()
        imageViewModel1.images.observe(viewLifecycleOwner, Observer {

            images = it as ArrayList<String>
            imageAdapter = ImageAdapter(this.requireContext(), images)
            image_gridview.adapter = imageAdapter

            image_gridview.setOnItemClickListener { adapterView, view, i, l ->

                var imageis = images[i].toString()
                var bundle = Bundle()
                bundle.putString("image",imageis)
                findNavController().navigate(R.id.action_tabFragment_to_fullScreenImageFragment,bundle)
            }
        })
    }

    private fun getImage() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent,REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null){

             uri = data.data!!

            var bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver,uri)

            val dir11 = requireContext().filesDir
            val mypath11 = File(dir11,getfilename(uri!!))

            try {
               val fos = FileOutputStream(mypath11)
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.close()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            val path:String = mypath11.absolutePath

            var single_image = ImageData(0,path)
            imageViewModel1.addImage(single_image)

            images.add(path)
            imageAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("Range")
    private fun getfilename(uri: Uri): String {

        var result:String? =  null
        if (uri.scheme.equals("content")){
            var cursor: Cursor? = requireContext().contentResolver.query(uri,null,null,null,null)
            try {
                if (cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
            finally {
                cursor!!.close()
            }
        }
        if (result == null){
            result = uri.path
            var cut:Int = result!!.lastIndexOf('/')
            if (cut != null){
                result = result.substring(cut + 1)
            }
        }
         return result
    }
}







