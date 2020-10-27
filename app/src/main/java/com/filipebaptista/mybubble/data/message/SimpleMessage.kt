package com.filipebaptista.mybubble.data.message

import androidx.annotation.DrawableRes

data class SimpleMessage(
    val id: Int,
    val sender: String,
    val text: String,
    @DrawableRes val image: Int
)
