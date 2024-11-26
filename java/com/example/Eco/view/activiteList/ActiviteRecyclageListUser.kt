package com.example.Eco.view.activiteList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Eco.R
import com.example.Eco.view.updateActivite.UpdateActiviteRecyclage
import com.example.Eco.viewmodel.ActiviteRecyclageViewModel


class ActiviteRecyclageListUser : AppCompatActivity() {


    lateinit var recyclerAdapter: ActiviteRecyclageUserAdapter
    private lateinit var activiteRecyclageRecycleview: RecyclerView
    var userId: String = "26f9c2613c922b783e8d6b2c"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userlist_activiterecyclage)

        activiteRecyclageRecycleview = findViewById(R.id.activiteRecyclageRecycleview)

        initRecyclerView()
        initViewModel()

    }


    private fun initRecyclerView() {
        activiteRecyclageRecycleview.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = ActiviteRecyclageUserAdapter(this)
        activiteRecyclageRecycleview.adapter = recyclerAdapter
        recyclerAdapter.setOnItemClickListener(object : ActiviteRecyclageUserAdapter.OnItemClickListener {
            override fun onItemClick(activiteId: String,activiteDate:String,activiteMaterial:String,quantity: Int) {
                val intent = Intent(this@ActiviteRecyclageListUser, UpdateActiviteRecyclage::class.java)
                intent.putExtra("activiteId", activiteId)
                intent.putExtra("activiteDate", activiteDate)
                intent.putExtra("activiteMaterial", activiteMaterial)
                intent.putExtra("quantity", quantity)
                startActivity(intent)
            }
        })
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        val viewModel: ActiviteRecyclageViewModel =
            ViewModelProvider(owner = this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setActiviteList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getActivitesByUser(userId)
    }
}