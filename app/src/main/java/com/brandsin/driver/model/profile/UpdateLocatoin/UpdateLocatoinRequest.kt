package com.brandsin.driver.model.profile.UpdateLocatoin

import com.google.gson.annotations.SerializedName

data class UpdateLocatoinRequest (
    @SerializedName("driver_id") var driver_id: Int? = null,
    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lng") var lng: Double? = null

)