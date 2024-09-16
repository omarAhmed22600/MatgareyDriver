package com.brandsin.driver.model.auth.register

import com.google.gson.annotations.SerializedName
import java.io.File

data class RegisterRequest(
        @SerializedName("user[name]") var name: String? = null,
        @SerializedName("user[last_name]") var last_name: String? = null,
        @SerializedName("user[phone_number]") var phone_number: String? = null,
        @SerializedName("user[email]") var email: String? = null,
        @SerializedName("user[password]") var password: String? = null,
        @SerializedName("driver[lat]") var lat: String? = null,
        @SerializedName("driver[lng]") var lng: String? = null,
        @SerializedName("driver[vehicle_type]") var vehicle_type: String? = null,
        @SerializedName("driver[vehicle_number]") var vehicle_number: String? = null,
        @SerializedName("driver[driving_licence]") var driving_licence: String? = null,
        @SerializedName("national_id_image") var national_id_image: File? = null,
        @SerializedName("driving_licence_image") var driving_licence_image: File? = null,
        @SerializedName("driver[store_id]") var store_id: String? = null,
        @SerializedName("user-picture") var user_picture: File? = null,
        @SerializedName("vehicle-image") var vehicle_image: File? = null,
        @SerializedName("lang") var lang: String? = null
)