package com.brandsin.driver.ui.main.home.neworders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brandsin.driver.R
import com.brandsin.driver.databinding.RawNewOrderBinding
import com.brandsin.driver.utils.SingleLiveEvent
import com.brandsin.driver.model.main.homepage.OrdersItem
import java.util.*

class NewOrdersAdapter : RecyclerView.Adapter<NewOrdersAdapter.NewOrdersHolder>()
{
    var itemsList: List<OrdersItem> = ArrayList()
    var newOrderItemLiveData = SingleLiveEvent<OrdersItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewOrdersHolder
    {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding: RawNewOrderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_new_order, parent, false)
        return NewOrdersHolder(binding)
    }

    override fun onBindViewHolder(holder: NewOrdersHolder, position: Int) 
    {
        val itemViewModel = ItemNewOrderViewModel(itemsList[position])
        holder.binding.viewModel = itemViewModel

        holder.binding.rawLayout.setOnClickListener {
            newOrderItemLiveData.value = itemViewModel.item
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun updateList(models: List<OrdersItem>) {
        itemsList = models
        notifyDataSetChanged()
    }

    inner class NewOrdersHolder(val binding: RawNewOrderBinding) : RecyclerView.ViewHolder(binding.root)
}
