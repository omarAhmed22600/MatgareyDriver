package com.brandsin.driver.model.main.order

import com.google.gson.annotations.SerializedName

data class CancelOrderResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
