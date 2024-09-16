package com.brandsin.driver.ui.main.orderdetails

import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.main.orderdetails.ItemsItem
import java.util.*
import kotlin.collections.ArrayList


class ItemOrderContentViewModel(var item: ItemsItem) : BaseViewModel()
{
    var contents = ObservableField<String>()
    val obsPrice = ObservableField<Double>()
    val obsCount = ObservableField<Int>()
    private val contentsList = ArrayList<String>()

    init
    {
            var Price = item.amount!!.toDouble()
        var totalPrice:Double=Price*item.quantity!!
            obsPrice.set(String.format(Locale.ENGLISH, "%.2f", totalPrice).toDouble())
            item.quantity.let {
                obsCount.set(item.quantity)
            }
        when {
            item.addons?.isNotEmpty() == true && item.addons?.size != 0 -> {
                item.addons?.forEach { item ->
                    contentsList.add(item?.name.toString())
                    contentsList.add(item?.price.toString())
                }

                contents.set(contentsList.joinToString { it -> it })

                obsSize.set(1)
            }
            else -> {
                obsSize.set(0)
            }
        }
    }
}