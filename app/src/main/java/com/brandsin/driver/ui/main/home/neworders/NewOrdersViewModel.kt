package com.brandsin.driver.ui.main.home.neworders

import android.util.Log
import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.constants.Codes

import com.brandsin.driver.model.profile.UpdateLocatoin.UpdateLocatoinRequest
import com.brandsin.driver.model.profile.UpdateLocatoin.UpdateLocatoinResponse
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.ui.main.home.Order
import com.brandsin.driver.ui.main.home.OrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

class NewOrdersViewModel : BaseViewModel()
{
    var newOrdersAdapter = NewOrdersAdapter()
    var obsLat = ObservableField<Double>()
    var obsLang = ObservableField<Double>()
    var request = UpdateLocatoinRequest()


    init
    {
        getUserStatus()
    }

    fun getUserStatus() {
        getNewOrders()
        obsIsLogin.set(true)
        /*when {
            PrefMethods.getUserData() != null -> {
                obsIsLogin.set(true)
                getNotifications()
            }
            else -> {
                obsIsLogin.set(false)
            }
        }*/
    }

    fun onLoginClicked() {
        setValue(Codes.LOGIN_CLICKED)
    }

    private fun getNewOrders()
    {
        obsIsEmpty.set(false)
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<OrderResponse?>({
            withContext(Dispatchers.IO) {
                return@withContext getApiRepo().getStoreOrders(
                    PrefMethods.getLanguage(),
                    PrefMethods.getUserData()!!.driver!!.id!!.toInt(),
                    "new"
                )
            }
        })
        { res ->
            Timber.e("back with result $res")
            obsIsLoading.set(false)
            when (res!!.success) {
                true -> {
                    res.let {
                        when {
                            it.orders!!.isNotEmpty() -> {
                                when (it.orders.size) {
                                    0 -> {
                                        obsIsFull.set(false)
                                        obsIsEmpty.set(true)
                                    }
                                    else -> {
                                        obsIsFull.set(true)
                                        obsIsEmpty.set(false)
                                        newOrdersAdapter.updateList(it.orders as ArrayList<Order>)
                                    }
                                }
                            }
                            else -> {
                                obsIsEmpty.set(true)
                                obsIsFull.set(false)
                            }
                        }
                    }
                }
                else -> {
                    obsIsEmpty.set(true)
                    obsIsFull.set(false)
                }
            }
        }
    }
    fun apiupdateLocation(){
        request.driver_id = PrefMethods.getUserData()!!.driver!!.id!!.toInt()
        Log.d("latc",obsLat.get().toString())
        request.lat=obsLat.get()
        Log.d("latc",obsLang.get().toString())
        request.lng=obsLang.get()
        val baeRepo = BaseRepository()
        val responseCall: Call<UpdateLocatoinResponse?> = baeRepo.apiInterface.updateLocation(request)
        responseCall.enqueue(object : Callback<UpdateLocatoinResponse?> {
            override fun onResponse(call: Call<UpdateLocatoinResponse?>, response: Response<UpdateLocatoinResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success!!) {

                    }else{

                    }
                } else {

                }
            }
            override fun onFailure(call: Call<UpdateLocatoinResponse?>, t: Throwable) {
                setValue(t.message!!)
                setShowProgress(false)
            }
        })
    }
}