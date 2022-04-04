package com.example.android.todotask

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todotask.models.Task
import java.text.SimpleDateFormat
/*import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter*/
import java.util.*

class MyAdapter(val context: Context,val task:List<Task>,val userInterface: userInterface):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){




 lateinit var myAdapter:MyAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view =inflater.inflate(R.layout.item_view,parent,false)
        return MyViewHolder(view,userInterface)

    }

    val status_task = arrayOf("Pending", "Running", "Done")


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

       val taskPosition = task[position]
        holder.title.text = taskPosition.Title
        //holder.date.text = taskPosition.Date
        holder.status.text = taskPosition.Status
        holder.description.text = taskPosition.Description


        //for date
        var date_is = taskPosition.Date

        var sdf: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        var date1:Date = sdf.parse(date_is)

        var day = DateFormat.format("dd",date1)
        var month = DateFormat.format("MMM",date1)


        /*Log.d("date_is1",date_is)

        var dateformat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var date = LocalDate.parse(date_is,dateformat)

        var day =  date.dayOfMonth.toString()
        var month = date.month.toString()

        Log.d("day", date.dayOfMonth.toString())
        Log.d("month",date.month.toString())
       // Log.d("date_isssssss", date.toString())*/




        holder.day.text = day
        holder.month.text = month




//       //convert string to date
//        var sdf:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
//            var date:Date = sdf.parse(date_is)
//
//        val cal = Calendar.getInstance()
//         cal.time = date
////        var year = cal.get(Calendar.YEAR)
////        var month = cal.get(Calendar.MONTH)
//        day = cal.get(Calendar.DAY_OF_MONTH).toString()
//        month = cal.get(Calendar.MONTH).toString()

        if(taskPosition.Status.equals("Pending")){
            holder.status.setBackgroundResource(R.drawable.status_red)
        }
        else if (taskPosition.Status.equals("Running")){
            holder.status.setBackgroundResource(R.drawable.status_yellow)
        }
        else{
            holder.status.setBackgroundResource(R.drawable.status_green)
        }
    }
    override fun getItemCount(): Int {
       return task.size
    }

    class MyViewHolder(itemview: View, userInterface: userInterface):RecyclerView.ViewHolder(itemview) {

        var title = itemview.findViewById<TextView>(R.id.title)
       // var date = itemview.findViewById<TextView>(R.id.date)
        var status = itemview.findViewById<TextView>(R.id.status)
        var description = itemview.findViewById<TextView>(R.id.description)
        var day = itemview.findViewById<TextView>(R.id.day)
        var month = itemview.findViewById<TextView>(R.id.month)


        init {
            itemview.setOnClickListener {
                userInterface.onItemClick(adapterPosition)

            }

        }

    }



}

