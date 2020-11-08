package com.filipebaptista.mybubble

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.filipebaptista.mybubble.ui.bubble.DetailFragment
import com.filipebaptista.mybubble.ui.main.MainFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_VIEW == intent.action) {
            val message = intent.data?.lastPathSegment
            if (message != null) {
                openDetail(message)
            }
        }
    }

    private fun openDetail(message: String) {
        supportFragmentManager.commit {
            replace(R.id.container, DetailFragment.newInstance(message))
        }
    }
}
