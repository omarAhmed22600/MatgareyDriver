package com.brandsin.driver.ui.main.home

data class Shipping(
    val deliveryOptionId: String,
    val gateway: String,
    val payment_reference: String,
    val payment_status: String,
//    val shipping_address: ShippingAddress
)