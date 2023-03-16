package com.iitp.anwesha.sponsors

data class SponsorResponse(
    val id: Int,
    val sponsor_name: String,
    val sponsor_phone_number: String,
    val sponsor_email: String,
    val sponsor_logo: String,
    val sponsor_link: String,
    val sponsor_instagram_id: String,
    val sponsor_facebook_id: String,
    val sponsor_linkdin_id: String
)