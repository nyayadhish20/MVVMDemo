package com.nyayadhish.ftttest.model

import androidx.annotation.DrawableRes

data class UserCardInfo(
    val name: String? = ""
    , val email: String? = ""
    , @DrawableRes val profileImage: Int? = 0
    , val start_time: String? = ""
    , val end_time: String? = ""
    , var time: String? = ""
    , var current_time: String? = end_time
)