package com.example.luckyface.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    val userId: String?,
    val userUid: String?,
    val active: String?,
    val address_created: String,
    val address_modified: String?,
    val city: String?,
    val complete_address: String?,
    val country: String?,
    val country_code: String?,
    val created: String?,
    val display_name: String?,
    val dob: String?,
    val email: String?,
    val file_name: String?,
    val file_type: String?,
    val first_name: String?,
    val folder_path: String?,
    val gender: String?,
    val home: String?,
    val last_name: String?,
    val modified: String?,
    val near_by: String?,
    val office: String?,
    val org_image: String?,
    val password: String?,
    val phone: String?,
    val pimg_created: String?,
    val pincode: String?,
    val state: String?,
    val street1: String?,
    val street2: String?,
    val thumb_image: String?,
    val xthumb_image: String?


) {
    @PrimaryKey(autoGenerate = false)
    var uidd: Int = CURRENT_USER_ID
}