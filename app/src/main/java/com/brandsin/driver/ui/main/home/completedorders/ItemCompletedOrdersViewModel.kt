package com.brandsin.driver.ui.main.home.completedorders

import android.util.Log
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.main.homepage.OrdersItem
import timber.log.Timber

class ItemCompletedOrdersViewModel(var item: OrdersItem) : BaseViewModel()
{
    fun onItemClicked()
    {
        Log.e("TAG", "onItemClicked: " )
        Timber.e("ss")
        setValue(item)
    }
}