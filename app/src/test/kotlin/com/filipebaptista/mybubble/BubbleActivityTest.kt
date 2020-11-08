package com.filipebaptista.mybubble

import android.content.Intent
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BubbleActivityTest {

    @Test
    fun shouldDisplayMessageDetailsWhenReceivingOpeningFromBubbleNotification() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            BubbleActivity::class.java
        ).apply {
            action = Intent.ACTION_VIEW
            data = "app://mybubble.filipebaptista.com/message/John Doe".toUri()
        }
        launchActivity<BubbleActivity>(intent)

        onView(withId(R.id.detailText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText("Hello John Doe")))
    }

}
