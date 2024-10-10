package com.brandsin.driver.ui.main.home

data class ShippingAddress(
    val address_1: String,
    val address_2: String,
    val city: City,
    val country: String,
    val landmark: Any,
    val phone_number: String,
//    val state: State,
    val street_name: String,
    val type: String,
    val user_id: Int,
    val zip: String
)