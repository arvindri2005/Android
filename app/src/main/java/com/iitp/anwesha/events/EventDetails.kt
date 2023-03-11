package com.iitp.anwesha.events

data class SoloRegistration(
    val message: String,
    val payment_details: PaymentDetails
)

data class PaymentDetails(
    val id: String,
    val entity: String,
    val amount: Int,
    val amount_paid: Int,
    val amount_due: Int,
    val currency: String = "INR",
    val receipt: String?,
    val offer_id: String?,
    val status: String,
    val attempts: Int,
//    val notes: List<>,
    val created_at: Long
)

data class TeamRegistration(
    val event_id: String,
    val team_name: String,
    val team_members: List<String>
)

data class TeamRegistrationResponse(
    val message: String,
    val payment_details: PaymentDetails,
    val team_id: String,
//    val error: List<>
)

data class RegistrationVerification(
    val razorpay_payment_id: String,
    val razorpay_order_id: String,
    val razorpay_signature: String
)

data class RegistrationVerificationResponse(
    val message: String,
    val error: String
)

data class Event(
    val id: String,
    val name: String,
    val organizer: String,
    val venue: String,
    val description: String,
    val start_time: String,
    val end_time: String,
    val prize : String,
    val registration_fee: String,
    val registration_deadline: String,
    val video: String,
    val poster: String,
    val tags: String,
    val max_team_size: Int,
    val min_team_size: Int,
    val is_active: Boolean,
    val is_online: Boolean,
    val registration_link: String,
    val order: Int,
    val payment_link: String,
    val payment_key: String
)