package com.brandsin.driver.model.auth.devicetoken

import com.google.gson.annotations.SerializedName

data class DeviceTokenResponse(

	@field:SerializedName("success")
	val success: Boolean? = null
)
