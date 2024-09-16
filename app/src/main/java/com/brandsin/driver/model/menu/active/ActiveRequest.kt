package com.brandsin.driver.model.menu.active

import com.google.gson.annotations.SerializedName

data class ActiveRequest (
        @SerializedName("driver_id") var driver_id: Int? = null,
        @SerializedName("is_working") var is_working: Int? = null
)