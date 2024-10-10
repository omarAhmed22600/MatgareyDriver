package com.brandsin.driver.ui.main.home

data class Billing(
    val billing_address: BillingAddress,
    val gateway: String,
    val payment_reference: String,
    val payment_status: String
)