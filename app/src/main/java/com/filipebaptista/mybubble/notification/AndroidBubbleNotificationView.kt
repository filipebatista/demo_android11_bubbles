package com.filipebaptista.mybubble.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.filipebaptista.mybubble.BubbleActivity
import com.filipebaptista.mybubble.MainActivity
import com.filipebaptista.mybubble.R
import com.filipebaptista.mybubble.data.message.SimpleMessage

class AndroidBubbleNotificationView(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val shortcutManager: ShortcutManager

) : BubbleNotification {

    companion object {
        private const val CHANNEL_NEW_BUBBLE = "new_bubble"

        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
    }

    init {
        setUpNotificationChannels()
        clearDynamicShortCuts()
    }

    override fun showNotification(simpleMessage: SimpleMessage) {
        //create person
        val icon = Icon.createWithResource(context, simpleMessage.image)
        val contentUri = createContentUri(simpleMessage.sender)


        val builder = getNotificationBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val person = Person.Builder()
                .setName(simpleMessage.sender)
                .setIcon(icon)
                .setImportant(true)
                .build()
            val bubbleData = createBubbleMetadata(contentUri, icon)

            val shortcut = createDynamicShortcut(
                simpleMessage,
                icon,
                person
            )
            shortcutManager.pushDynamicShortcut(shortcut)

            builder.setBubbleMetadata(bubbleData)
            builder.setStyle(
                Notification.MessagingStyle(person).addMessage(
                    Notification.MessagingStyle.Message(
                        simpleMessage.text,
                        System.currentTimeMillis(),
                        person
                    )
                )
            )
                .setShortcutId(shortcut.id)
                .addPerson(person)

        }

        // The user can turn off the bubble in system settings. In that case, this notification
        // is shown as a normal notification instead of a bubble. Make sure that this
        // notification works as a normal notification as well.
        builder.setContentTitle(
            context.resources.getString(
                R.string.message_from,
                simpleMessage.sender
            )
        )
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setShowWhen(true)

        notificationManager.notify(simpleMessage.id, builder.build())
    }

    private fun getNotificationBuilder(): Notification.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, CHANNEL_NEW_BUBBLE)
        } else {
            Notification.Builder(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun createBubbleMetadata(
        contentUri: Uri,
        icon: Icon
    ): Notification.BubbleMetadata {
        // Create bubble intent
        val bubbleIntent =
            PendingIntent.getActivity(
                context,
                REQUEST_BUBBLE,
                // Launch BubbleActivity as the expanded bubble.
                Intent(context, BubbleActivity::class.java)
                    .setAction(Intent.ACTION_VIEW)
                    .setData(contentUri),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        // Create bubble metadata
        return Notification.BubbleMetadata.Builder(bubbleIntent, icon)
            .setDesiredHeightResId(R.dimen.bubble_height)
            .setAutoExpandBubble(true)
            .setSuppressNotification(true)
            .build()
    }

    private fun setUpNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (notificationManager.getNotificationChannel(CHANNEL_NEW_BUBBLE) == null) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_NEW_BUBBLE,
                        context.getString(R.string.notification_channel_name_bubble),
                        // The importance must be IMPORTANCE_HIGH to show Bubbles.
                        NotificationManager.IMPORTANCE_HIGH
                    ).apply {
                        setAllowBubbles(true)
                    }
                )
            }
        }
    }

    private fun clearDynamicShortCuts() {
        shortcutManager.removeAllDynamicShortcuts()
    }

    private fun createContentUri(text: String): Uri {
        return "app://mybubble.filipebaptista.com/message/$text".toUri()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun createDynamicShortcut(
        message: SimpleMessage,
        icon: Icon,
        person: Person
    ): ShortcutInfo {
        return ShortcutInfo.Builder(context, message.id.toString())
            .setLongLived(true)
            .setIntent(
                Intent(context, MainActivity::class.java)
                    .setAction(Intent.ACTION_VIEW)
                    .setData(createContentUri(message.text))
            )
            .setShortLabel(message.sender)
            .setIcon(icon)
            .setPerson(person)
            .build()
    }
}
