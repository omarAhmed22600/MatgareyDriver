package com.brandsin.driver.model.auth

import com.google.gson.annotations.SerializedName

data class UserModel(

        @field:SerializedName("national_id")
        val nationalId: List<NationalIdItem?>? = null,

        @field:SerializedName("integration_id")
        val integrationId: Any? = null,

        @field:SerializedName("code")
        val code: Any? = null,

        @field:SerializedName("birthdate")
        val birthdate: Any? = null,

        @field:SerializedName("role")
        val role: String? = null,

        @field:SerializedName("confirmation_code")
        val confirmationCode: Any? = null,

        @field:SerializedName("gender")
        val gender: Any? = null,

        @field:SerializedName("android_token")
        val androidToken: Any? = null,

//        @field:SerializedName("created_at")
//        val createdAt: String? = null,

        @field:SerializedName("ios_token")
        val iosToken: Any? = null,

//        @field:SerializedName("updated_at")
//        val updatedAt: String? = null,

        @field:SerializedName("card_last_four")
        val cardLastFour: Any? = null,

        @field:SerializedName("card_brand")
        val cardBrand: Any? = null,

        @field:SerializedName("notification_preferences")
        val notificationPreferences: Any? = null,

        @field:SerializedName("provider_fb")
        val providerFb: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("confirmed_at")
        val confirmedAt: Any? = null,

        @field:SerializedName("job_title")
        val jobTitle: Any? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("address")
        val address: Any? = null,

        @field:SerializedName("phone_country_code")
        val phoneCountryCode: Any? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null,

//        @field:SerializedName("created_by")
//        val createdBy: Int? = null,

//        @field:SerializedName("deleted_at")
//        val deletedAt: Any? = null,

        @field:SerializedName("reward_points")
        val rewardPoints: Any? = null,

        @field:SerializedName("picture")
        val picture: String? = null,

        @field:SerializedName("picture_thumb")
        val pictureThumb: String? = null,

        @field:SerializedName("driver")
        val driver: Driver? = null,

        @field:SerializedName("name")
        val name: String? = null,

//        @field:SerializedName("updated_by")
//        val updatedBy: Int? = null,

        @field:SerializedName("provider_id")
        val providerId: Any? = null,

        @field:SerializedName("online")
        val online: Int? = null,

        @field:SerializedName("phone_number")
        val phoneNumber: String? = null,

        @field:SerializedName("trial_ends_at")
        val trialEndsAt: Any? = null,

        @field:SerializedName("payment_method_token")
        val paymentMethodToken: Any? = null,

        @field:SerializedName("gateway")
        val gateway: Any? = null,

        @field:SerializedName("properties")
        val properties: Any? = null,

        @field:SerializedName("country_id")
        val countryId: Int? = null,

        @field:SerializedName("city_id")
        val cityId: Int? = null,

        @field:SerializedName("status")
        val status: Int? = null
)

data class NationalIdItem(

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("url")
        val url: String? = null
)

data class Driver(

        @field:SerializedName("store_id")
        val storeId: Int? = null,

        @field:SerializedName("driving_licence")
        val drivingLicence: String? = null,

        @field:SerializedName("lng")
        val lng: String? = null,

        @field:SerializedName("vehicle_type")
        val vehicleType: String? = null,

        @field:SerializedName("is_working")
        var isWorking: Boolean? = null,

//        @field:SerializedName("created_at")
//        val createdAt: String? = null,
//
//        @field:SerializedName("created_by")
//        val createdBy: Int? = null,

//        @field:SerializedName("deleted_at")
//        val deletedAt: Any? = null,

        @field:SerializedName("vehicle_image")
        val vehicleImage: String? = null,

//        @field:SerializedName("updated_at")
//        val updatedAt: String? = null,

        @field:SerializedName("user_id")
        val userId: Int? = null,

        @field:SerializedName("vehicle_number")
        val vehicleNumber: String? = null,

//        @field:SerializedName("updated_by")
//        val updatedBy: Int? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("lat")
        val lat: String? = null
)