package com.example.vinyls

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class HU06Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(UserSelectionActivity::class.java)

    @Test
    fun checkCollectorDetail() {

        // Start session as Visitor
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.button3),
                ViewMatchers.withText("Visitante"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())

        // Click collector option menu.
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.navigation_collectors),
                ViewMatchers.withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())
        Thread.sleep(5000)

        // Select a Collector
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.collectorsRv),
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.collectorTitleTextView),
                ViewMatchers.withText("Manolo Bellon"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.withText("Manolo Bellon")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}