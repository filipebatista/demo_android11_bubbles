package com.filipebaptista.mybubble

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filipebaptista.mybubble.data.message.SimpleMessage
import com.filipebaptista.mybubble.notification.AndroidBubbleNotificationView
import junit.framework.TestCase.assertNull
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowNotificationManager


@RunWith(AndroidJUnit4::class)
class AndroidBubbleNotificationViewTest {
    private lateinit var notificationManager: NotificationManager
    private lateinit var shortcutManager: ShortcutManager
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        shortcutManager = context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.P])
    fun `Given a simpleMessage When showNotification is called Then a notification should be dispatched with no bubble metadata`() {
        val simpleMessage = SimpleMessage(0, "Jonh Doe", "Hello", 1)

        val notificationView = AndroidBubbleNotificationView(
            context,
            notificationManager,
            shortcutManager
        )

        notificationView.showNotification(simpleMessage)

        val shadowNotificationManager: ShadowNotificationManager = shadowOf(notificationManager)
        val notification: Notification = shadowNotificationManager.allNotifications.first()
        val bubbleMetadata: NotificationCompat.BubbleMetadata? =
            NotificationCompat.getBubbleMetadata(notification)

        assertThat(shadowNotificationManager.size(), equalTo(1))
        assertNull(notification.shortcutId)
        assertNull(bubbleMetadata)
    }

    @Test
    fun `Given a simpleMessage When showNotification is called Then a notification should be dispatched with bubble metadata`() {
        val simpleMessage = SimpleMessage(0, "Jonh Doe", "Hello", 1)

        val notificationView = AndroidBubbleNotificationView(
            context,
            notificationManager,
            shortcutManager
        )

        notificationView.showNotification(simpleMessage)

        val shadowNotificationManager: ShadowNotificationManager = shadowOf(notificationManager)
        val notification: Notification = shadowNotificationManager.allNotifications.first()
        val bubbleMetadata: NotificationCompat.BubbleMetadata? =
            NotificationCompat.getBubbleMetadata(notification)


        assertThat(shadowNotificationManager.size(), equalTo(1))
        assertNotNull(bubbleMetadata)
        assertNotNull(notification.shortcutId)
    }
}
