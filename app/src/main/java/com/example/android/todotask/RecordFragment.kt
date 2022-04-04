package com.example.android.todotask

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.todotask.models.Subtask
import com.example.android.todotask.viewModels.RecordViewModel
import kotlinx.android.synthetic.main.fragment_record.*
import kotlinx.android.synthetic.main.subtask.view.*


private const val ARG_PARAM1 = "position"
private const val ARG_PARAM2 = "id"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment(R.layout.fragment_record),subtaskinterface{

    private var position1:Int = 0
    private var id1:Int = 0
    lateinit var recordviewmodel:RecordViewModel
    lateinit var subtask_adapter:SubTaskAdapter
    var subtask2:ArrayList<Subtask> = ArrayList<Subtask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting bundle value
        arguments!!.let {
            position1 = it.getInt(ARG_PARAM1)
            id1 = it.getInt(ARG_PARAM2)
        }
        Log.d("p2", position1.toString())
        Log.d("id2", id1.toString())

        val repository = (activity!!.application as TODOApplication).userRepository
        recordviewmodel = ViewModelProvider(this,RecordViewModelFactory(repository)).get(RecordViewModel::class.java)

        ///recycler View
        recordviewmodel.getSubTask(id1)
        recordviewmodel.subtask1.observe(this, Observer {
            subtask2 = it as ArrayList<Subtask>
            Log.d("taskkkkkkkkkkk", subtask2.toString())
//            Log.d("taskkkkkkkkkkk_idBefore", subtask2.get(0).id.toString())

            subtask_recyclerview.layoutManager = LinearLayoutManager(this.context)
            subtask_adapter = SubTaskAdapter(context!!,subtask2,this)
            subtask_recyclerview.adapter = subtask_adapter

        })


        choose_record.setOnClickListener {
            //Inflate dialog with custom View
            val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.subtask,null)
            val mBuilder = AlertDialog.Builder(this.context)
                .setView(mDialogView)
                .setTitle("SubTask")

            val mAlertDialog = mBuilder.show()

            mDialogView.sub_save.setOnClickListener {
                val task1 = mDialogView.subtask1.text.toString()
                if (task1.isNotEmpty()){
                    val subtask = Subtask(0,task1,false,id1)
                    recordviewmodel.addSubTask(subtask)
                    mAlertDialog.dismiss()
                    subtask2.add(subtask)
                    subtask_adapter.notifyDataSetChanged()

                }
                else{
                    Toast.makeText(this.context," Please Enter Subtask Title",Toast.LENGTH_SHORT).show()
                }
            }

            mDialogView.sub_cancle.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    companion object{
        fun newInstance(position: Int, id: Int) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, position)
                    putInt(ARG_PARAM2, id)
                }
            }
    }

    override fun onSingleItemClick(position: Int, checked: Boolean) {

        var sub_task_position = subtask2[position]
        var subtask = subtask2[position].id
        Log.d("subtaskkkk", subtask.toString())

        if (checked){
            recordviewmodel.setCheckStatus(true,subtask)
        }
        else{
            recordviewmodel.setCheckStatus(false,subtask)
        }
//        if (sub_task_position.is_checked.equals(true)){
//            recordviewmodel.setCheckStatus(false,subtask)
//        }
//        if (sub_task_position.is_checked.equals(false)){
//            recordviewmodel.setCheckStatus(true,subtask)
//        }


    }

}
