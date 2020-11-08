package com.filipebaptista.mybubble

import android.content.Intent
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    lateinit var scenario: ActivityScenario<MainActivity>

    @Test
    fun shouldDisplayButtonToTriggerBubbleNotification() {
        scenario = launchActivity()

        onView(withId(R.id.btnShowBubble))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText(R.string.btn_show_bubble_notification)))
            .check(matches(isClickable()))
    }

    @Test
    fun shouldDisplayMessageDetailsWhenOpeningNotification() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MainActivity::class.java
        ).apply {
            action = Intent.ACTION_VIEW
            data = "app://mybubble.filipebaptista.com/message/John Doe".toUri()
        }
        scenario = launchActivity(intent)

        onView(withId(R.id.detailText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText("Hello John Doe")))
    }
}
