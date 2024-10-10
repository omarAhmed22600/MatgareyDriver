package com.brandsin.driver.ui.profile

import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.menu.active.ActiveRequest
import com.brandsin.driver.model.menu.active.ActiveResponse
import com.brandsin.driver.utils.PrefMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : BaseViewModel()
{
    var activeRequest = ActiveRequest()


    fun onLanguageClicked()
    {
        setValue(Codes.LANGUAGE_CLICKED)
    }

    fun onProfileClicked()
    {
        setValue(Codes.EDIT_CLICKED)
    }

    fun onVehicleClicked()
    {
        setValue(Codes.VEHICLE_CLICKED)
    }

    fun onChangePassClicked()
    {
        setValue(Codes.CHANGE_PASS_CLICKED)
    }
    fun setActive(i: Int) {
        activeRequest.is_working = i
        activeRequest.driver_id = PrefMethods.getUserData()!!.driver!!.id!!.toInt()
        val baeRepo = BaseRepository()
        val responseCall: Call<ActiveResponse?> = baeRepo.apiInterface.setActive(activeRequest)
        responseCall.enqueue(object : Callback<ActiveResponse?> {
            override fun onResponse(call: Call<ActiveResponse?>, response: Response<ActiveResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success!!) {
                        PrefMethods.saveUserData(response.body()!!.user)
                        PrefMethods.saveLoginState(true)
                    }else{
                        setValue(response.body()!!.message.toString())
                    }
                } else {
                    setValue(response.message())
                }
            }
            override fun onFailure(call: Call<ActiveResponse?>, t: Throwable) {
                setValue(t.message!!)
                setShowProgress(false)
            }
        })
    }

}