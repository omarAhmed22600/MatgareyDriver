package com.brandsin.driver.ui.main.orderdetails

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.brandsin.driver.R
import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.main.orderdetails.*
import com.brandsin.driver.network.requestCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class OrderDetailsViewModel : BaseViewModel()
{
    var orderContentsAdapter  = OrderContentsAdapter()
    var orderDetails = OrderDetailsResponse()
    var item = ItemsItem()//دية انا مزودهاا
    var obsTotal  = ObservableField<Double>()
    var obsDeliveryPrice= ObservableField<Double>()
    var obsPaymentWay  = ObservableField<String>()
    var isMapReady = MutableLiveData<Boolean>()
    var obsAcceptWay = ObservableField<String>()
    var obsUserName = ObservableField<String>()
    var obsOrderNumber = ObservableField<String>()
    val obsPrice = ObservableField<Double>()
    val obsCount = ObservableField<Int>()


    var updateStatusRequest = UpdateStatusRequest()
    var allItemChecked = false
    var allItemSize = 0
    
    fun onCallTextClicked()
    {
        setValue(Codes.CALL_CLICKED)
    }
    fun onCallImgClicked() {
        setValue(Codes.CALL_CLICKED)
    }

    fun onCallStoreImgClicked() {
        setValue(Codes.CALL_STORE_CLICKED)
    }
    fun getOrderDetails(orderId: Int)
    {
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<OrderDetailsResponse?>({
            withContext(Dispatchers.IO) {
                return@withContext getApiRepo().getOrderDetails(orderId, PrefMethods.getLanguage())
            }
        })
        { res ->
            when (res!!.success) {
                true -> {
                    obsIsLoading.set(false)
                    obsIsFull.set(true)


                    //if(res.items!!.isEmpty()){
                        res.offers!!.forEach {
                          var item : ItemsItem=ItemsItem()
                            item.id=it!!.id
                            item.amount=it.amount
                            item.product_description=it.offer!!.description
                            item.quantity=it.quantity
                            item.skuCode=it.sku_code
                            item.productName=it.offer!!.name
                            item.userNotes=it.user_notes
                            item.type=it.type
                            item.image=it.offer!!.image
                            item.addons=null
                            item.storeId=null
                            res.items!!.add(item)
                        }
                  //  }

                    orderContentsAdapter.updateList(res.items as ArrayList<ItemsItem>,res.order!!.status.toString())
                    //orderContentsAdapter.updateList(res.offers as ArrayList<offerItem>,res.order!!.status.toString())

                    getTotalPrice(res.items as ArrayList<ItemsItem>)
                    orderDetails = res
                    isMapReady.value = true
                    if(res.order!!.billing!!.gateway.equals("Cash")){
                        obsPaymentWay.set("نقدا")
                    }

                    notifyChange()
                    obsUserName.set("${res.order!!.firstName} ${res.order!!.firstName}")
                    obsOrderNumber.set("${getString(R.string.order_number)}: ${res.order!!.orderNumber}")

                    if (res.order!!.store!!.deliveryPrice.isNullOrEmpty()){
                        obsDeliveryPrice.set(0.0)
                    }else{
                        obsDeliveryPrice.set(res.order!!.store!!.deliveryPrice!!.toDouble())
                    }

                    allItemSize = orderDetails.items!!.size
                    setValue(Codes.SUCCESS)
                    when {
                        res.total!!.isEmpty() -> {
                            obsTotal.set(0.0)
                        }
                        else -> {
                            when (res.total!![0]!!.total) {
                                null -> {
                                    obsTotal.set(0.0)
                                }
                                else -> {
                                    obsTotal.set(res.total!![0]!!.total!!.toDouble())
                                }
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }

    fun getTotalPrice(items: ArrayList<ItemsItem>) {
        var totalPrice: Double =0.0
            items.forEach {
                var Price = it.amount!!.toDouble()
                totalPrice+= Price //* it.quantity!!
                it.quantity.let {it1->
                    obsCount.set(it1)
                }
        }
        obsPrice.set(String.format(Locale.ENGLISH, "%.2f", totalPrice).toDouble())


    }
    fun onPrepareClicked() {
        setClickable()
        if ( orderDetails.order!!.status!!.contains("accepted")){
            if (PrefMethods.getUserData()!!.driver!!.storeId != null
                    && PrefMethods.getUserData()!!.driver!!.storeId.toString().isNotEmpty()){
                if (orderDetails.order!!.status!! == "accepted_by_driver"){
                    setUpdateStatus("shipping")
                }else{
                    setUpdateStatus("accepted_by_driver")
                }
            }else{
                setUpdateStatus("shipping")
            }
        }else if ( orderDetails.order!!.status == "shipping"){
            if (allItemChecked) {
                setUpdateStatus("shipped")
            }else{
                setValue(Codes.ALL_ITEM_NOT_CHECKED)
            }
        }else if ( orderDetails.order!!.status == "shipped"){
            setValue(Codes.OPEN_MAP)
        }
    }
    fun setUpdateStatus( updateStatus: String) {
        setShowProgress(true)
        updateStatusRequest.driver_id = PrefMethods.getUserData()!!.driver!!.id!!.toInt()
        updateStatusRequest.order_id = orderDetails.order!!.id!!.toInt()
        updateStatusRequest.status = updateStatus
        updateStatusRequest.lang = PrefMethods.getLanguage()
        val baeRepo = BaseRepository()
        val responseCall: Call< UpdateStatusResponse?> = baeRepo.apiInterface.setUpdateStatus(updateStatusRequest)
        responseCall.enqueue(object : Callback< UpdateStatusResponse?> {
            override fun onResponse(call: Call< UpdateStatusResponse?>, response: Response< UpdateStatusResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success!!) {
                        orderDetails.order!!.status = response.body()!!.data!!.status.toString()
                        setValue(Codes.UPDATE_STATUS)
                    }else{
                        setValue(response.body()!!.message.toString())
                    }
                } else {
                    setValue(response.message())
                }
            }
            override fun onFailure(call: Call< UpdateStatusResponse?>, t: Throwable) {
                setValue(t.message!!)
                setShowProgress(false)
            }
        })
    }
}
