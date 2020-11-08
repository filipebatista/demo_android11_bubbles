package com.filipebaptista.mybubble

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.filipebaptista.mybubble.ui.bubble.DetailFragment

class BubbleActivity : AppCompatActivity(R.layout.bubble_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.data?.lastPathSegment ?: return

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(R.id.container, DetailFragment.newInstance(message))
            }
        }
    }
}
