package com.brandsin.driver.ui.main.reports.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brandsin.driver.R
import com.brandsin.driver.databinding.RawReportBinding
import com.brandsin.driver.model.main.reports.DataItem
import java.util.*

class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.OrderReportsHolder>()
{
    var itemsList = ArrayList<DataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderReportsHolder
    {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding: RawReportBinding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_report, parent, false)
        return OrderReportsHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderReportsHolder, position: Int)
    {
        val itemViewModel = ItemReportViewModel(itemsList[position])
        holder.binding.viewModel = itemViewModel

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun updateList(models: ArrayList<DataItem>) {
        itemsList = models
        notifyDataSetChanged()
    }

    inner class OrderReportsHolder(val binding: RawReportBinding) : RecyclerView.ViewHolder(binding.root)
    {

    }
}
