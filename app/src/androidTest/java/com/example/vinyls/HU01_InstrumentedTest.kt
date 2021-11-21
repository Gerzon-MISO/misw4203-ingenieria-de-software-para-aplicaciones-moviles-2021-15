package com.example.vinyls

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HU01Test {

    @Rule
    @JvmField
    var  mActivityTestRule: ActivityScenarioRule<UserSelectionActivity> =
        ActivityScenarioRule(UserSelectionActivity::class.java)

    @Test
    fun testHU01() {

        // Inicia con usuario "Visitante"
        val visitorsBtn =
            onView(allOf(withId(R.id.button3), withText("VISITANTE"),
                isDisplayed()
            ))
        visitorsBtn.check(matches(isDisplayed()))
        visitorsBtn.perform(click())
        Thread.sleep(5000)

        //Revisa que exista el boton de album y lo pulsa
        val albumsBtn =
            onView(allOf(withId(R.id.navigation_albums),
                isDisplayed()
            ))
        albumsBtn.check(matches(isDisplayed()))
        albumsBtn.perform(click())
        Thread.sleep(5000)

        //Revisa que este listado el album Poeta del pueblo
        val albumText =
            onView(allOf(withId(R.id.textView), withText("Poeta del pueblo"),
                isDisplayed()
            ))
       albumText.check(matches(isDisplayed()))
    }
}