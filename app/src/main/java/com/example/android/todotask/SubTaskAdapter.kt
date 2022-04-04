package com.example.android.todotask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todotask.models.Subtask

class SubTaskAdapter(val context:Context,val subtask:List<Subtask>,val subtaskinterface: subtaskinterface):RecyclerView.Adapter<SubTaskAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SubTaskAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.subtask_item_view,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: SubTaskAdapter.MyViewHolder, position: Int) {
        val subtaskposition = subtask[position]
        holder.Sub_title.text =subtaskposition.Title
        holder.Check.isChecked = subtaskposition.is_checked

        holder.Check.setOnClickListener {
            subtaskinterface.onSingleItemClick(position, holder.Check.isChecked)
        }
    }

    override fun getItemCount(): Int{
       return subtask.size
    }

    class MyViewHolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        var View_sub = itemView.findViewById<View>(R.id.view_sub)
        var Sub_title = itemview.findViewById<TextView>(R.id.sub_title)
        var Check = itemview.findViewById<CheckBox>(R.id.check1)

    }


}
