package com.example.Eco.view.activiteList

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Eco.R
import com.example.Eco.data.ActiviteRecyclage
import java.io.File


class ActiviteRecyclageAdapter(val activity: Activity) :
    RecyclerView.Adapter<ActiviteRecyclageAdapter.MyViewHolder>() {

    private var activiteList: List<ActiviteRecyclage>? = null
    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(
            activiteId: String,
            activiteDate: String,
            activiteMaterial: String,
            quantity: Int,
            image: String
        )

        fun onButtonClick(position: Int)
    }

    fun setActiviteList(activiteList: List<ActiviteRecyclage>?) {
        this.activiteList = activiteList
    }
    fun getActiviteList(): List<ActiviteRecyclage> {
        return activiteList ?: emptyList() // Retourne une liste vide si activiteList est null
    }

    fun getItemAtPosition(position: Int): ActiviteRecyclage? {
        return activiteList?.getOrNull(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activite_list_row, parent, false)
        return MyViewHolder(view)
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentActivite = activiteList?.get(position)!!
        holder.Bind(currentActivite, activity)
        holder.itemView.setOnClickListener {
            currentActivite._id?.let { it1 ->
                itemClickListener?.onItemClick(
                    it1,
                    currentActivite.dateAndTime,
                    currentActivite.recyclableMaterial,
                    currentActivite.quantity,
                    currentActivite.image
                )
            }
        }
    }









    override fun getItemCount(): Int {
        if (activiteList == null) return 0
        else return activiteList?.size!!
    }









    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvrecyclableMaterial: TextView = view.findViewById(R.id.tvrecyclableMaterial)
        val tvdateAndTime: TextView = view.findViewById(R.id.tvdateAndTime)
        val tvquantity: TextView = view.findViewById(R.id.tvquantity)
        val imageViewSelectedImage: ImageView = view.findViewById(R.id.imageViewSelectedImage) // ImageView pour afficher l'image

        fun Bind(data: ActiviteRecyclage, activity: Activity) {
            tvrecyclableMaterial.text = data.recyclableMaterial
            tvdateAndTime.text = data.dateAndTime
            tvquantity.text = data.quantity.toString()

            // Charger l'image avec Glide
            if (data.image.isNotEmpty()) {
                Glide.with(activity)
                    .load(File(data.image))  // Charger depuis le fichier interne
                    .into(imageViewSelectedImage)  // Charger dans l'ImageView
            } else {
                imageViewSelectedImage.setImageResource(R.drawable.remove) // Image par défaut
            }
        }

    }

}
