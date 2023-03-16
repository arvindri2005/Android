package com.iitp.anwesha.profile

data class ProfileResponse(
    val anwesha_id: String,
    val full_name: String,
    val phone_number: String,
    val email_id: String,
    val college_name: String?,
    val age: Int?,
    val is_email_verified: Boolean,
    val gender: String?,
    val is_profile_completed: Boolean,
    val qr_code: String
)

data class QrResponse(val qr_code: String)

data class MyEventDetails(
    val event_id: String,
    val event_name: String,
    val event_start_time: String,
    val event_end_time: String,
    val event_venue: String,
    val event_tags: String,
    val event_is_active: Boolean,
    val order_id: String,
    val payment_done: Boolean,
    val payment_url: String
)

data class MyEvents(
    val solo: List<MyEventDetails>,
    val team: List<MyEventDetails>
)

data class UpdateProfile(
    val phone_number: String,
    val full_name: String,
    val college_name: String? = "",
    val gender: String? = "",
    val age: Int? = 0,
    val user_type: String? = "new_user_type",
    val instagram_id: String? = "new instagram_id",
    val facebook_id: String? = "New_facebook_id"
)