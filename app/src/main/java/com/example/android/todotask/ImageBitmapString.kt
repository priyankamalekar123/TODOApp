package com.example.android.todotask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageBitmapString {

@TypeConverter
 fun getStringFromBitmap(bitmapimg:Bitmap): ByteArray{
     var bitypeArrayBitmapString = ByteArrayOutputStream()
     bitmapimg.compress(Bitmap.CompressFormat.PNG,100,bitypeArrayBitmapString)
     var byteArray = bitypeArrayBitmapString.toByteArray()
    return byteArray
 }

@TypeConverter
  fun getBitmapFromStr(array:ByteArray):Bitmap{
      return BitmapFactory.decodeByteArray(array,0,array.size)
  }
}