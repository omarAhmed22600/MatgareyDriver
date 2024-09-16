package com.brandsin.driver.model.main.orderdetails

import com.google.gson.annotations.SerializedName

data class UpdateStatusRequest (

    @SerializedName("driver_id")
    var driver_id: Int? = null,

    @SerializedName("order_id")
    var order_id: Int? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("lang")
    var lang: String? = null,
)