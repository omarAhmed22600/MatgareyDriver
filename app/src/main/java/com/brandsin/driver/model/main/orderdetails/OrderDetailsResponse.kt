package com.brandsin.driver.model.main.orderdetails

import com.google.gson.annotations.SerializedName

data class OrderDetailsResponse(

	@field:SerializedName("offers")
	var offers: List<offerItem?>? = null,

	@field:SerializedName("total")
	var total: List<TotalItem?>? = null,

	@field:SerializedName("payment")
	var payment: Payment? = null,

	@field:SerializedName("shipping")
	var shipping: ShippingD? = null,

	@field:SerializedName("success")
	var success: Boolean? = null,

	@field:SerializedName("discount")
	var discount: Any? = null,

	@field:SerializedName("items")
	var items: ArrayList<ItemsItem?>? = null,

	@field:SerializedName("order")
	var order: Order? = null
)

data class Payment(
	@field:SerializedName("subtotal")
	var subtotal: Double? = null,
	@field:SerializedName("taxes")
	var taxes: Double? = null,
	@field:SerializedName("delivery")
	var delivery: Double? = null,
)

data class CreatedAt(

	@field:SerializedName("date")
	var date: String? = null,

	@field:SerializedName("timezone")
	var timezone: String? = null,

	@field:SerializedName("timezone_type")
	var timezoneType: Int? = null
)
data class offerItem(
	@field:SerializedName("sku_code")
	var sku_code: String? = null,
	@field:SerializedName("amount")
    var amount: String? = null,
    @field:SerializedName("id")
    var id: Int? = null,
	@field:SerializedName("description")
	var description: String? = null,
	@field:SerializedName("quantity")
	var quantity: Int? = null,
	@field:SerializedName("type")
	var type: String? = null,
	@field:SerializedName("item_options")
	var item_options: String? = null,
	@field:SerializedName("user_notes")
	var user_notes: String? = null,
	@field:SerializedName("created_at")
	var created_at: String? = null,
	@field:SerializedName("offer")
	var offer:offerItem2? = null,

)
data class offerItem2(

	@field:SerializedName("id")
	var id: Int? = null,
	@field:SerializedName("name")
	var name: String? = null,
	@field:SerializedName("description")
	var description: String? = null,
	@field:SerializedName("price")
	var price: Int? = null,
	@field:SerializedName("price_to")
	var price_to: Int? = null,
	@field:SerializedName("start_date")
	var start_date: String? = null,

	@field:SerializedName("end_date")
	var end_date: String? = null,

	@field:SerializedName("store_id")
	var store_id: Int? = null,

	@field:SerializedName("active")
	var active: Int? = null,

	@field:SerializedName("created_by")
	var created_by: Int? = null,

	@field:SerializedName("updated_by")
	var updated_by: Int? = null,
	@field:SerializedName("deleted_at")
	var deleted_at: String? = null,
	@field:SerializedName("created_at")
	var created_at: String? = null,
	@field:SerializedName("updated_at")
	var updated_at: String? = null,
	@field:SerializedName("image")
	var image: String? = null,
	@field:SerializedName("name_en")
	var name_en: String? = null,
	@field:SerializedName("description_en")
	var description_en: String? = null,

)

data class BillingAddressD(

	@field:SerializedName("zip")
	var zip: String? = null,

	@field:SerializedName("country")
	var country: String? = null,

	@field:SerializedName("city")
	var city: String? = null,

	@field:SerializedName("user_id")
	var userId: Int? = null,

	@field:SerializedName("address_1")
	var address1: String? = null,

	@field:SerializedName("address_2")
	var address2: String? = null,

	@field:SerializedName("phone_number")
	var phoneNumber: String? = null,

	@field:SerializedName("state")
	var state: String? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("street_name")
	var streetName: String? = null
)

data class Order(

	@field:SerializedName("order_number")
	var orderNumber: String? = null,

	@field:SerializedName("user_notes")
	var userNotes: Any? = null,

	@field:SerializedName("delivery_time")
	var deliveryTime: String? = null,

	@field:SerializedName("street_name")
	var streetName: String? = null,

	@field:SerializedName("billing")
	var billing: BillingD? = null,

	@field:SerializedName("city_name")
	var cityName: Any? = null,

	@field:SerializedName("state_name")
	var stateName: Any? = null,

	@field:SerializedName("country_name")
	var countryName: String? = null,

	@field:SerializedName("store_name")
	var storeName: String? = null,

	@field:SerializedName("currency")
	var currency: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("state_id")
	var stateId: Int? = null,

	@field:SerializedName("first_name")
	var firstName: String? = null,

	@field:SerializedName("lat")
	var lat: String? = null,

	@field:SerializedName("discount_id")
	var discountId: Any? = null,

	@field:SerializedName("store_id")
	var storeId: Int? = null,

	@field:SerializedName("amount")
	var amount: String? = null,

	@field:SerializedName("address")
	var address: Address? = null,

	@field:SerializedName("comments")
	var comments: List<Any?>? = null,

	@field:SerializedName("lng")
	var lng: String? = null,

	@field:SerializedName("address_id")
	var addressId: Int? = null,

	@field:SerializedName("last_name")
	var lastName: String? = null,

	@field:SerializedName("store")
	var store: StoreD? = null,

	@field:SerializedName("user_id")
	var userId: Int? = null,

	@field:SerializedName("is_rated")
	var isRated: Boolean? = null,

	@field:SerializedName("phone_number")
	var phoneNumber: String? = null,

	@field:SerializedName("user")
	var user: User? = null,

	@field:SerializedName("country_id")
	var countryId: Int? = null,

	@field:SerializedName("status")
	var status: String? = null,

	@field:SerializedName("city_id")
	var cityId: Int? = null
)

data class ItemsItem(

	@field:SerializedName("store_id")
	var storeId: Int? = null,

	@field:SerializedName("image")
	var image: String? = null,

	@field:SerializedName("amount")
	var amount: String? = null,

	@field:SerializedName("quantity")
	var quantity: Int? = null,

	@field:SerializedName("addons")
	var addons: List<AddonsItem?>? = null,

	@field:SerializedName("user_notes")//
	var userNotes: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("product_description")
	var product_description: String? = null,

	@field:SerializedName("created_at")
	var createdAt: CreatedAt? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("product_name")
	var productName: String? = null,

	@field:SerializedName("product_id")
	var productId: Int? = null,

	@field:SerializedName("store_name")
	var storeName: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("sku_code")
	var skuCode: String? = null
)
data class AddonsItem(

		@field:SerializedName("store_id")
		val storeId: Int? = null,

		@field:SerializedName("price")
		val price: Int? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null
)
data class StoreD(

	@field:SerializedName("floor_no")
	var floorNo: Any? = null,

	@field:SerializedName("short_description")
	var shortDescription: Any? = null,

	@field:SerializedName("account_type")
	var accountType: Any? = null,

	@field:SerializedName("parking_domain")
	var parkingDomain: Any? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("registration_file")
	var registrationFile: Any? = null,

	@field:SerializedName("delivery_time")
	var deliveryTime: Int? = null,

	@field:SerializedName("code_name")
	var codeName: Any? = null,

	@field:SerializedName("land_mark")
	var landMark: Any? = null,

	@field:SerializedName("causer_type")
	var causerType: String? = null,

	@field:SerializedName("updated_at")
	var updatedAt: String? = null,

	@field:SerializedName("avg_rating")
	var avgRating: Double? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("slug")
	var slug: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("lat")
	var lat: String? = null,

	@field:SerializedName("image")
	var image: String? = null,

	@field:SerializedName("thumbnail")
	var thumbnail: String? = null,

	@field:SerializedName("address")
	var address: String? = null,

	@field:SerializedName("lng")
	var lng: String? = null,

	@field:SerializedName("building_no")
	var buildingNo: Any? = null,

	@field:SerializedName("created_by")
	var createdBy: Int? = null,

	@field:SerializedName("deleted_at")
	var deletedAt: Any? = null,

	@field:SerializedName("phone_number2")
	var phoneNumber2: String? = null,

	@field:SerializedName("min_price_product")
	var minPriceProduct: String? = null,

	@field:SerializedName("registration_file2")
	var registrationFile2: Any? = null,

	@field:SerializedName("causer_id")
	var causerId: Int? = null,

	@field:SerializedName("offer_flag")
	var offerFlag: Int? = null,

	@field:SerializedName("user_id")
	var userId: Int? = null,

	@field:SerializedName("apartment_no")
	var apartmentNo: Any? = null,

	@field:SerializedName("registration_image2")
	var registrationImage2: Any? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("fixed_commission_price")
	var fixedCommissionPrice: Any? = null,

	@field:SerializedName("registration_image")
	var registrationImage: Any? = null,

	@field:SerializedName("updated_by")
	var updatedBy: Int? = null,

	@field:SerializedName("new_flag")
	var newFlag: Int? = null,

	@field:SerializedName("phone_number")
	var phoneNumber: String? = null,

	@field:SerializedName("is_featured")
	var isFeatured: Int? = null,

	@field:SerializedName("status")
	var status: String? = null,

	@field:SerializedName("delivery_price")
	var deliveryPrice: String? = null
)

data class ShippingD(

	@field:SerializedName("amount")
	var amount: String? = null,

	@field:SerializedName("item_options")
	var itemOptions: Any? = null,

	@field:SerializedName("quantity")
	var quantity: Int? = null,

	@field:SerializedName("user_notes")
	var userNotes: Any? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("sku_code")
	var skuCode: String? = null
)

data class Address(

	@field:SerializedName("lng")
	var lng: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("type_label")
	var typeLabel: String? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("is_default")
	var isDefault: Int? = null,

	@field:SerializedName("lat")
	var lat: String? = null
)

data class BillingD(

	@field:SerializedName("payment_status")
	var paymentStatus: String? = null,

	@field:SerializedName("billing_address")
	var billingAddress: BillingAddressD? = null,

	@field:SerializedName("payment_reference")
	var paymentReference: String? = null,

	@field:SerializedName("gateway")
	var gateway: String? = null
)

data class TotalItem(

	@field:SerializedName("total")
	var total: String? = null,

	@field:SerializedName("sub_total")
	var subTotal: String? = null
)

data class User(

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("last_name")
	var lastName: Any? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("picture")
	var picture: String? = null,

	@field:SerializedName("picture_thumb")
	var pictureThumb: String? = null
)
