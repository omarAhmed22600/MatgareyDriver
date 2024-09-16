package com.brandsin.driver.model.profile.UpdateLocatoin

data class UpdateLocatoinResponse(
    val `data`: Data,
    val success: Boolean
)

data class Data(
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val driving_licence: String,
    val driving_licence_image: String,
    val id: Int,
    val is_working: Boolean,
    val lat: String,
    val lng: String,
    val store_id: Int,
    val updated_at: String,
    val updated_by: Int,
    val user_id: Int,
    val vehicle_image: String,
    val vehicle_number: String,
    val vehicle_type: String
)