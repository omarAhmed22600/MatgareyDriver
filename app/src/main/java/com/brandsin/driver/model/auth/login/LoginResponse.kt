package com.brandsin.driver.model.auth.login

import com.google.gson.annotations.SerializedName
import com.brandsin.driver.model.auth.UserModel

data class LoginResponse(

	@field:SerializedName("success")
	var success: Boolean? = null,

	@field:SerializedName("message")
	var message: String? = null,

	@field:SerializedName("user")
	var user: UserModel? = null
)