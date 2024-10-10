package com.brandsin.driver.ui.activity.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.brandsin.driver.R
import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.MyApp
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.menu.active.ActiveRequest
import com.brandsin.driver.model.menu.active.ActiveResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : BaseViewModel()
{
    val obsShowToolbar = ObservableBoolean()
    val obsTitle = ObservableField<String>()
    val obsUserName = ObservableField<String>()
    val obsBtnLogout = ObservableField<String>()
    private val obsLanguage = ObservableField<String>()
    val userImg = MutableLiveData("")

    var activeRequest = ActiveRequest()

    fun setUpUserData()
    {
        obsIsLogin.set(true)
        obsUserName.set(MyApp.context.getString(R.string.name))
        obsBtnLogout.set(MyApp.context.getString(R.string.information_account))
        Timber.e("image is ${PrefMethods.getUserData()?.picture.orEmpty()}")
        if (PrefMethods.getUserData() == null) {
            obsIsLogin.set(false)
            obsUserName.set(MyApp.context.getString(R.string.welcome))
            obsBtnLogout.set(MyApp.context.getString(R.string.login))
        } else {
            obsIsLogin.set(true)
            obsBtnLogout.set(MyApp.context.getString(R.string.information_account))
            obsUserName.set(PrefMethods.getUserData()?.name.orEmpty())
            Timber.e("image is ${PrefMethods.getUserData()?.picture.orEmpty()}")
            userImg.value = PrefMethods.getUserData()?.picture.orEmpty()
        }
    }

    init
    {
        setUpUserData()
    }

    fun onLogoutClicked() {
        PrefMethods.saveLoginState(false)
        PrefMethods.deleteUserData()
        setValue(Codes.LOGOUT_CLICK)
    }

    fun onEditClicked() {
        if (PrefMethods.getUserData() != null){
            setValue(Codes.EDIT_CLICKED)
        } else {
            setValue(Codes.BUTTON_LOGIN_CLICKED)
        }
    }

   /* fun setActive(i: Int) {
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
    }*/


}