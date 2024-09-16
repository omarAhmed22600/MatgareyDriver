package com.brandsin.driver.ui.main.home

import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenRequest
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenResponse
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.utils.PrefMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class HomeViewModel : BaseViewModel()
{
    var deviceTokenRequest = DeviceTokenRequest()

    fun onNotificationsClicked()
    {
        setValue(Codes.NOTIFICATION_CLICK)
    }
    fun deviceToken(){

        deviceTokenRequest.user_id = PrefMethods.getUserData()!!.id.toString()
        deviceTokenRequest.type = "android_token"

        val baeRepo = BaseRepository()
        val responseCall: Call<DeviceTokenResponse?> = baeRepo.apiInterface.deviceToken(deviceTokenRequest)
        responseCall.enqueue(object : Callback<DeviceTokenResponse?> {
            override fun onResponse(call: Call<DeviceTokenResponse?>, response: Response<DeviceTokenResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success!!) {

                    }
                } else {
                    setValue(response.message())
                }
            }
            override fun onFailure(call: Call<DeviceTokenResponse?>, t: Throwable) {
                setValue(t.message!!)
                setShowProgress(false)
            }
        })
    }

}