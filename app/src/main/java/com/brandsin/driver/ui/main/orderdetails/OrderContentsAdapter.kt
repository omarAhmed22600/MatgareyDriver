package com.brandsin.driver.ui.main.orderdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brandsin.driver.R
import com.brandsin.driver.databinding.RawOrderContentBinding
import com.brandsin.driver.model.main.orderdetails.ItemsItem
import com.brandsin.driver.utils.SingleLiveEvent
import java.util.*

class OrderContentsAdapter : RecyclerView.Adapter<OrderContentsAdapter.OrderContentsHolder>()
{
    //viewModel.obsPrice
    var orderStatus = ""
    var itemsList: ArrayList<ItemsItem> = ArrayList()
    var orderItemLiveData = SingleLiveEvent<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderContentsHolder
    {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding: RawOrderContentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_order_content, parent, false)
        return OrderContentsHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderContentsHolder, position: Int)
    {
        val itemViewModel = ItemOrderContentViewModel(itemsList[position])
        holder.binding.viewModel = itemViewModel

        if (orderStatus == "shipped" || orderStatus == "completed"){
            holder.binding.imgSelected.setImageResource(R.drawable.ic_right_enabled)
        }else{
            holder.binding.imgSelected.setImageResource(R.drawable.ic_right_disabled)
        }

        holder.binding.rawLayout.setOnClickListener {
            if (orderStatus != "shipped" && orderStatus != "completed") {
                holder.binding.imgSelected.setImageResource(R.drawable.ic_right_enabled)
                orderItemLiveData.value = itemViewModel.item
            }
        }


        when (position) {
            itemsList.size-1 -> {
                holder.binding.seperator.visibility = View.GONE
            }

        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun updateList(models: ArrayList<ItemsItem>, status: String) {
        itemsList = models
        orderStatus = status
        notifyDataSetChanged()
    }

    inner class OrderContentsHolder(val binding: RawOrderContentBinding) : RecyclerView.ViewHolder(binding.root)
    {

    }
}
