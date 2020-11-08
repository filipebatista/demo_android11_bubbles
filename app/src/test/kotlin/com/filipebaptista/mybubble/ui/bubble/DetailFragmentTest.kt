package com.filipebaptista.mybubble.ui.bubble

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.filipebaptista.mybubble.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @Test
    fun shouldShowDetailsOfTheReceivedMessage() {
        val arguments = DetailFragment.newInstance("John Doe").arguments
        launchFragmentInContainer<DetailFragment>(arguments)

        onView(withId(R.id.detailText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(withText("Hello John Doe")))
    }
}
