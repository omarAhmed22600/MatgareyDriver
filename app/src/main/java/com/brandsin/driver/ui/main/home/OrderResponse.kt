package com.brandsin.driver.ui.main.home

data class OrderResponse(
    val orders: List<Order>,
    val success: Boolean
)