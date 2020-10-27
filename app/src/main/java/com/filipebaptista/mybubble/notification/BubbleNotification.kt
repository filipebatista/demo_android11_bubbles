package com.filipebaptista.mybubble.notification

import com.filipebaptista.mybubble.data.message.SimpleMessage

interface BubbleNotification {
    fun showNotification(simpleMessage: SimpleMessage)
}
