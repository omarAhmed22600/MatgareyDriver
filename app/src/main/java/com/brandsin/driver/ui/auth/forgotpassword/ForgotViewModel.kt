package com.brandsin.driver.ui.auth.forgotpassword

import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.auth.forgot.ForgotPassRequest
import com.brandsin.driver.model.auth.forgot.ForgotPassResponse
import com.brandsin.driver.model.constants.Codes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotViewModel : BaseViewModel()
{
    var request = ForgotPassRequest()
    var code=""
    var userId=""

    fun onNextClicked() {
        setClickable()
        when {
            request.phone_email.isNullOrEmpty() || request.phone_email.isNullOrBlank() -> {
                setValue(Codes.EMPTY_PHONE)
            }
            request.phone_email!!.length < 10 -> {
                setValue(Codes.INVALID_PHONE)
            }
            else -> {
                setShowProgress(true)
                apiResendCode()
            }
        }
    }
    fun apiResendCode(){
        request.lang= PrefMethods.getLanguage()
        val baeRepo = BaseRepository()
        val responseCall: Call<ForgotPassResponse?> = baeRepo.apiInterface.forgotPass(request)
        responseCall.enqueue(object : Callback<ForgotPassResponse?> {
            override fun onResponse(call: Call<ForgotPassResponse?>, response: Response<ForgotPassResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.success!!) {
                        code= response.body()!!.code.toString()
                        userId= response.body()!!.user_id.toString()
                        setValue(Codes.FORGOT_SUCCESS)
                    }else{
                        setValue(response.body()!!.error.toString())
                    }
                } else {
                    setValue(response.message())
                }
            }
            override fun onFailure(call: Call<ForgotPassResponse?>, t: Throwable) {
                setValue(t.message!!)
                setShowProgress(false)
            }
        })
    }
}
