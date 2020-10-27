package com.filipebaptista.mybubble.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.filipebaptista.mybubble.R
import com.filipebaptista.mybubble.data.message.SimpleMessage
import com.filipebaptista.mybubble.notification.BubbleNotification
import kotlin.random.Random

class MainViewModel(
    application: Application,
    private val bubbleNotification: BubbleNotification
) : AndroidViewModel(application) {

    fun showBubbleNotification() {
        bubbleNotification.showNotification(getRandomMessage())
    }

    private fun getRandomMessage(): SimpleMessage {
        return SimpleMessage(100, "James", "Hello", R.drawable.ic_base_person)
    }
}
