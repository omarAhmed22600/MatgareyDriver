package com.brandsin.driver.model.auth.register

import com.google.gson.annotations.SerializedName
import com.brandsin.driver.model.auth.UserModel

data class RegisterResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserModel? = null
)

