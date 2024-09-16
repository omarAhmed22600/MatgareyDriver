package com.brandsin.driver.ui.auth.register

import android.util.Patterns
import com.brandsin.driver.database.BaseRepository
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenRequest
import com.brandsin.driver.model.auth.devicetoken.DeviceTokenResponse
import com.brandsin.driver.model.auth.register.RegisterRequest
import com.brandsin.driver.model.auth.register.RegisterResponse
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.network.ApiResponse
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.utils.PrefMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : BaseViewModel() {

    var storeCode = "0"
    var request = RegisterRequest()
    var deviceTokenRequest = DeviceTokenRequest()

    fun onLocationClicked() {
        setValue(Codes.LOCATION_CLICKED)
    }

    fun onNationalIdPhotoClicked() {
        setValue(Codes.SELECT_NationalId_IMAGE)
    }

    fun onChangeNationalIdPhotoClicked() {
        setValue(Codes.SELECT_NationalId_IMAGE)
    }

    fun onDriverLicencePhotoClicked() {
        setValue(Codes.SELECT_DriverLicence_IMAGE)
    }

    fun onChangeDriverLicencePhotoClicked() {
        setValue(Codes.SELECT_DriverLicence_IMAGE)
    }

    fun onVehiclePhotoClicked() {
        setValue(Codes.SELECT_Vehicle_IMAGE)
    }

    fun onChangeVehiclePhotoClicked() {
        setValue(Codes.SELECT_Vehicle_IMAGE)
    }
    fun onVehicleTypeClicked() {
        setValue(Codes.SELECT_VehicleType)
    }

    fun onUserPicClicked() {
        setValue(Codes.SELECT_UserPic_IMAGE)
    }
    fun onRegisterClicked() {

        when {
            request.user_picture == null -> {
                setValue(Codes.EMPTY_UserPic_THUMB)
            }
            request.name == null -> {
                setValue(Codes.NAME_EMPTY)
            }
            request.last_name == null -> {
                setValue(Codes.EMPTY_LAST_NAME)
            }
            request.phone_number == null -> {
                setValue(Codes.EMPTY_PHONE)
            }
            request.phone_number!!.length < 10 -> {
                setValue(Codes.INVALID_PHONE)
            }
            request.email.isNullOrEmpty() ||  request.email.isNullOrBlank() -> {
                setValue(Codes.EMAIL_EMPTY)
            }
            !Patterns.EMAIL_ADDRESS.matcher(request.email).matches() -> {
                setValue(Codes.EMAIL_INVALID)
            }
            request.lat == null -> {
                setValue(Codes.EMPTY_DRIVER_LAT)
            }
            request.national_id_image == null -> {
                setValue(Codes.EMPTY_NationalId_IMAGE)
            }
            request.driving_licence_image == null -> {
                setValue(Codes.EMPTY_DriverLicence_THUMB)
            }
           storeCode == "1" && request.store_id == null -> {
                setValue(Codes.EMPTY_STORE_CODE)
            }
            request.vehicle_type == null -> {
                setValue(Codes.EMPTY_VehicleType)
            }
            request.vehicle_number == null -> {
                setValue(Codes.EMPTY_VehicleNumber)
            }
            request.driving_licence == null -> {
                setValue(Codes.EMPTY_DrivingLicence)
            }
            request.password == null -> {
                setValue(Codes.PASSWORD_EMPTY)
            }
            request.password!!.length < 6 -> {
                setValue(Codes.PASSWORD_SHORT)
            }
            request.vehicle_image == null -> {
                setValue(Codes.EMPTY_Vehicle_THUMB)
            }
            else -> { 
                setShowProgress(true)
                apiRegister() 
            } 
        }
    }
    fun apiRegister(){
        if (request.store_id==null){
            request.store_id=""
        }
        request.lang= PrefMethods.getLanguage()
        requestCall<RegisterResponse?>({
            withContext(Dispatchers.IO) {
                return@withContext getApiRepo().setRegister(request) }
        })
        { res ->
            setShowProgress(false)
            when (res!!.success) {
                true -> {
                    deviceToken(res.user!!.id)
                    apiResponseLiveData.value = ApiResponse.success(res)
                }
                else -> {
                    apiResponseLiveData.value = ApiResponse.errorMessage(res.message.toString())
                }
            }
        }
    }
    fun deviceToken(id: Int?) {

        deviceTokenRequest.user_id = id.toString()
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