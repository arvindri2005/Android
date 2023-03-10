package com.college.anwesha2k23.events

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