package com.college.anwesha2k23.profile

data class ProfileResponse(
    val anwesha_id: String,
    val full_name: String,
    val phone_number: String,
    val email_id: String,
    val college_name: String,
    val age: Int,
    val is_email_verified: Boolean,
    val gender: String,
    val is_profile_completed: Boolean
)

data class MyEventDetails(
    val event_id: String,
    val event_name: String,
    val event_start_time: String,
    val event_end_time: String,
    val event_venue: String = "IITP",
    val event_tags: String,
    val event_is_active: Boolean,
    val order_id: String,
    val payment_done: Boolean,

)

data class MyEvents(
    val solo: List<MyEventDetails>,
    val team: List<MyEventDetails>
)