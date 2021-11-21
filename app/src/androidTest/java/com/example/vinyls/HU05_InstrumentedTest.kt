package com.example.vinyls

//import android.R
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.vinyls.ui.activities.MainActivity
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.BaseMatcher





/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HU05_InstrumentedTest {

    @Rule
    @JvmField
    var  mActivityTestRule: ActivityScenarioRule<UserSelectionActivity> =
        ActivityScenarioRule(UserSelectionActivity::class.java)

    @Test
    fun testHU05() {

        // Inicia con usuario "Usuario"
        val visitorsBtn =
            onView(allOf(withId(R.id.button4), withText("USUARIO"),
                //withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)),
                isDisplayed()
            ))
        visitorsBtn.check(matches(isDisplayed()))
        visitorsBtn.perform(click())
        Thread.sleep(1000)

        //Seleccionar Collectors
        val collectorsMenu =
            onView(allOf(
                withContentDescription(R.string.title_collectors),
                isDisplayed()
            ))
        collectorsMenu.check(matches(isDisplayed()))
        collectorsMenu.perform(click())
        Thread.sleep(1000)

        //Se lista ... el nombre de Un Collector
        val collectorNameText =
            onView(allOf(withId(R.id.textView),withText("Manolo Bellon"),
                isDisplayed()
            ))
        collectorNameText.check(matches(isDisplayed()))

        //Se lista ... La imagen de un collector
        val imageView = onView(
            first(
                withId(R.id.imageView)
            )
        )
        imageView.check(matches(isDisplayed()))



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
    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
}