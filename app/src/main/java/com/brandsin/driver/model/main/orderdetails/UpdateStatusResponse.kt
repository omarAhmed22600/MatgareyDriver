package com.brandsin.driver.model.main.orderdetails

import com.google.gson.annotations.SerializedName

data class UpdateStatusResponse(

	
	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	var message: String? = null,
)
