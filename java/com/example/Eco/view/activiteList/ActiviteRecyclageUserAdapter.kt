package com.example.Eco.view.activiteList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Eco.R
import com.example.Eco.data.ActiviteRecyclage
import com.example.Eco.data.MaterialStatistics

class ActiviteRecyclageUserAdapter(val activity:Activity): RecyclerView.Adapter<ActiviteRecyclageUserAdapter.MyViewHolder>(){

    private var activiteList: List<ActiviteRecyclage>?=null
    private var statsList: List<MaterialStatistics>?=null
    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(activiteId: String,activiteDate:String,activiteMaterial:String,quantity: Int)
    }

    fun setActiviteList(activiteList: List<ActiviteRecyclage>){
        this.activiteList = activiteList
    }
    fun setstatsList(statsList: List<MaterialStatistics>){
        this.statsList = statsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_activite_list_row,parent,false)
        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentActivite = activiteList?.get(position)!!
        holder.Bind(currentActivite,activity)
        holder.itemView.setOnClickListener {
            currentActivite._id?.let { it1 -> itemClickListener?.onItemClick(it1,currentActivite.dateAndTime,currentActivite.recyclableMaterial,currentActivite.quantity) }
        }
    }


    override fun getItemCount(): Int {
        if(activiteList == null )return 0
        else return activiteList?.size!!
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvrecyclableMaterial: TextView = view.findViewById(R.id.tvrecyclableMaterial)
        val tvdateAndTime: TextView = view.findViewById(R.id.tvdateAndTime)
        val tvquantity: TextView = view.findViewById(R.id.tvquantity)


        fun Bind(data: ActiviteRecyclage,activity:Activity){
            tvrecyclableMaterial.text = data.recyclableMaterial
            tvdateAndTime.text = data.dateAndTime
            tvquantity.text = data.quantity.toString()
        }

        }

}