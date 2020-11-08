package com.filipebaptista.mybubble

import android.app.NotificationManager
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filipebaptista.mybubble.ui.main.MainFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @Test
    fun shouldTriggerNotificationWhenClickingInMainButton() {
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.btnShowBubble))
            .perform(click())

        val shadowNotificationManager = Shadows.shadowOf(
            ApplicationProvider.getApplicationContext<Context>().getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
        )

        val allNotifications = shadowNotificationManager.allNotifications
        assertEquals(1, allNotifications.size)
    }
}
