package com.brandsin.driver.model.menu.active

import com.google.gson.annotations.SerializedName
import com.brandsin.driver.model.auth.UserModel

data class ActiveResponse(

	@field:SerializedName("success")
	var success: Boolean? = null,

	@field:SerializedName("message")
	var message: String? = null,

	@field:SerializedName("user")
	var user: UserModel? = null
)
