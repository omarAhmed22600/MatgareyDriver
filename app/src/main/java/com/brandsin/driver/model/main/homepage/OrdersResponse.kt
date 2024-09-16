package com.brandsin.driver.model.main.homepage

import com.google.gson.annotations.SerializedName
import com.brandsin.driver.model.auth.UserModel
import java.io.Serializable

data class OrdersResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null
): Serializable

data class ShippingAddress(

	@field:SerializedName("zip")
	val zip: String? = null,

//	@field:SerializedName("country")
//	val country: String? = null,
//
//	@field:SerializedName("city")
//	val city: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("address_1")
	val address1: String? = null,

	@field:SerializedName("address_2")
	val address2: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("street_name")
	val streetName: String? = null
): Serializable

data class OrdersItem(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("driver_id")
	val driverId: Int? = null,

	@field:SerializedName("orders_key")
	val ordersKey: String? = null,

	@field:SerializedName("comments")
	val comments: List<CommentsItem?>? = null,

	@field:SerializedName("order_number")
	val orderNumber: String? = null,

	@field:SerializedName("user_notes")
	val userNotes: Any? = null,

	@field:SerializedName("address_id")
	val addressId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("delivery_time")
	val deliveryTime: String? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("billing")
	val billing: Billing? = null,

	@field:SerializedName("shipping")
	val shipping: Shipping? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("is_rated")
	val isRated: Boolean? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("user")
	val user: UserModel? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("discount_id")
	val discountId: Any? = null
): Serializable

data class Shipping(

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("shipping_address")
	val shippingAddress: ShippingAddress? = null,

	@field:SerializedName("payment_reference")
	val paymentReference: String? = null,

	@field:SerializedName("gateway")
	val gateway: String? = null
): Serializable

data class BillingAddress(

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("address_1")
	val address1: String? = null,

	@field:SerializedName("address_2")
	val address2: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("street_name")
	val streetName: String? = null
): Serializable

data class Billing(

	@field:SerializedName("payment_status")
	val paymentStatus: String? = null,

	@field:SerializedName("billing_address")
	val billingAddress: BillingAddress? = null,

	@field:SerializedName("payment_reference")
	val paymentReference: String? = null,

	@field:SerializedName("gateway")
	val gateway: String? = null
): Serializable

data class CommentsItem(

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("author_type")
	val authorType: String? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("user_rate")
	val userRate: Int? = null,

	@field:SerializedName("commentable_type")
	val commentableType: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_info")
	val userInfo: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("commentable_id")
	val commentableId: Int? = null,

	@field:SerializedName("author_id")
	val authorId: Int? = null,

	@field:SerializedName("user_rating")
	val userRating: Int? = null
): Serializable

data class Country(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("media")
	val media: List<Any?>? = null,

	@field:SerializedName("created_by")
	val createdBy: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null
): Serializable
