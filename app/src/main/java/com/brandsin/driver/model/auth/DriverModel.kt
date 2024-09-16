package com.brandsin.driver.model.auth

import com.google.gson.annotations.SerializedName

data class DriverModel(

    @field:SerializedName("store_id")
    var storeId: Int? = null,

    @field:SerializedName("driving_licence")
    var drivingLicence: String? = null,

    @field:SerializedName("lng")
    var lng: String? = null,

    @field:SerializedName("vehicle_type")
    var vehicleType: String? = null,

//    @field:SerializedName("created_at")
//    var createdAt: String? = null,
//
//    @field:SerializedName("created_by")
//    var createdBy: Int? = null,
//
//    @field:SerializedName("deleted_at")
//    var deletedAt: Any? = null,
//
//    @field:SerializedName("updated_at")
//    var updatedAt: String? = null,

    @field:SerializedName("user_id")
    var userId: Int? = null,

    @field:SerializedName("vehicle_number")
    var vehicleNumber: String? = null,

//    @field:SerializedName("updated_by")
//    var updatedBy: Int? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("lat")
    var lat: String? = null
)