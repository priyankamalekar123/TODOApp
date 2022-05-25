package com.example.android.todotask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

const val channnelId = "notification_channel"
const val channnelName = "com.example.android.todotask"
class MyFirebaseMessagingService: FirebaseMessagingService() {

    
    override fun onNewToken(token: String) {
        Log.d("token",token)
        super.onNewToken(token)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       // super.onMessageReceived(remoteMessage)
       // getFirebasemsg(remoteMessage.notification().getTitle(),)

        if(remoteMessage.notification != null){
            getFirebasemsg(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }

    //generate the notification
    //attach the notification created with custom layout
    //show the notification

    fun getRemoteView(title:String,msg:String):RemoteViews{
        val remoteViews = RemoteViews("com.example.android.todotask",R.layout.notification)

        remoteViews.setTextViewText(R.id.notification_title,title)
        remoteViews.setTextViewText(R.id.notification_msg,msg)
        remoteViews.setImageViewResource(R.id.notification_img,R.drawable.person_icon)

        return remoteViews
    }

    fun getFirebasemsg(title:String,msg:String){

        val i = Intent(this,MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT)

        //channel id & channel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channnelId)
            .setSmallIcon(R.drawable.person_icon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,msg))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationchannel = NotificationChannel(channnelId, channnelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationchannel)
        }

        notificationManager.notify(0,builder.build())

    }
}