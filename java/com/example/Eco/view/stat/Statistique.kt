package com.example.Eco.view.stat


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.Eco.R
import com.example.Eco.data.MaterialStatistics
import com.example.Eco.databinding.StatistiqueBinding
import com.example.Eco.view.activiteList.ActiviteRecyclageUserAdapter
import com.example.Eco.viewmodel.ActiviteRecyclageViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


class Statistique: AppCompatActivity()  {

    val profitValue=  ArrayList<PieEntry>()

    private lateinit var recyclerAdapter: ActiviteRecyclageUserAdapter
    private lateinit var binding: StatistiqueBinding
    @SuppressLint("MissingInflatedId")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= StatistiqueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerAdapter = ActiviteRecyclageUserAdapter(this)
        recyclerAdapter.setOnItemClickListener(object : ActiviteRecyclageUserAdapter.OnItemClickListener {
            override fun onItemClick(activiteId: String, activiteDate: String, activiteMaterial: String, quantity: Int) {
                // Handle item click if needed
            }
        })
        initStatsViewModel()
    }

    fun dataListing(stats: List<MaterialStatistics>){
        profitValue.clear()
        for (stat in stats) {
            profitValue.add(PieEntry(stat.count.toFloat(), stat._id))
        }
        setChart()
    }

    private fun setChart() {
        val pieDataSetter : PieDataSet
        if (binding.chart.data != null && binding.chart.data.dataSetCount>0){
            pieDataSetter = binding.chart.data.getDataSetByIndex(0) as PieDataSet
            pieDataSetter.values = profitValue
            binding.chart.data.notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        }else{
            pieDataSetter = PieDataSet(profitValue,"Data Set")
            pieDataSetter.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            pieDataSetter.setDrawValues(false)
            pieDataSetter.sliceSpace = 3f
            pieDataSetter.iconsOffset = MPPointF(10f,10f)
            pieDataSetter.selectionShift = 10f

            val dataSets = ArrayList<IPieDataSet>()
            dataSets.add(pieDataSetter)

            val data = PieData(pieDataSetter)
            binding.chart.data = data
            binding.chart.invalidate()
            binding.chart.description.isEnabled = false


        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initStatsViewModel() {
        val viewModel: ActiviteRecyclageViewModel =
            ViewModelProvider(owner = this).get(ActiviteRecyclageViewModel::class.java)
        viewModel.getLiveDataStatsObserver().observe(this) { stats ->
            if (stats != null) {
                dataListing(stats)
                recyclerAdapter.setstatsList(stats) // Assuming setActiviteList is the appropriate method to set data in your adapter
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting statistics", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getStatis()
    }

}