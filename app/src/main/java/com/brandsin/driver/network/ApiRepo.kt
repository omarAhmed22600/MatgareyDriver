package com.brandsin.driver.network

import com.brandsin.driver.database.ApiInterface
import com.brandsin.driver.model.auth.register.RegisterRequest
import com.brandsin.driver.model.menu.notifications.ReadNotificationRequest
import com.brandsin.store.utils.map.toMultiPart

/**
 * Created by MouazSalah on 28/12/2020.
 **/

class ApiRepo(private val apiInterface: ApiInterface) {

    suspend fun setRegister(request : RegisterRequest) = apiInterface.register(
            request.name!!.toRequestBodyParam(),
            request.last_name!!.toRequestBodyParam(),
            request.phone_number!!.toRequestBodyParam(),
            request.email!!.toRequestBodyParam(),
            request.password!!.toRequestBodyParam(),
            request.lat!!.toRequestBodyParam(),
            request.lng!!.toRequestBodyParam(),
            request.vehicle_type!!.toRequestBodyParam(),
            request.vehicle_number!!.toRequestBodyParam(),
            request.driving_licence!!.toRequestBodyParam(),
            request.national_id_image?.toMultiPart("national_id_image")!!,
            request.driving_licence_image?.toMultiPart("driving_licence_image")!!,
            request.store_id!!.toRequestBodyParam(),
            request.user_picture?.toMultiPart("user-picture")!!,
            request.vehicle_image?.toMultiPart("vehicle-image")!!,
            request.lang!!.toRequestBodyParam()
    )

    suspend fun getStoreOrders(lang : String , driver_id : Int ,status : String) = apiInterface.getStoreOrders(lang, driver_id  , status )

    suspend fun getNotifications(limit : Int, page : Int, userId : Int) = apiInterface.getNotifications(limit, page, userId)

    suspend fun readNotification(request : ReadNotificationRequest) = apiInterface.readNotification(request)

    suspend fun getCommonQues(type : String, lang : String) = apiInterface.getCommonQues(type, lang)

    suspend fun getHelpQues(type : String, lang : String) = apiInterface.getHelpQues(type, lang)

    suspend fun getPhoneNumber(type : String, lang : String) = apiInterface.getPhoneNumber(type, lang)

    suspend fun getSocialLinks(type : String, lang : String) = apiInterface.getSocialLinks(type , lang)

    suspend fun getOrderDetails(orderId : Int, lang : String) = apiInterface.getOrderDetails(orderId , lang)

    suspend fun getReports(driver_id : Int,limit : Int,page : Int,type : String,from : String, to : String) = apiInterface.getReports(driver_id, limit, page, type, from, to)


}