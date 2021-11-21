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
class HU02Test {

    @Rule
    @JvmField
    var  mActivityTestRule: ActivityScenarioRule<UserSelectionActivity> =
        ActivityScenarioRule(UserSelectionActivity::class.java)

    @Test
    fun testHU02() {

        // Inicia con usuario "Usuario"
        val visitorsBtn =
            onView(allOf(withId(R.id.button4), withText("USUARIO"),
                isDisplayed()
            ))
        visitorsBtn.check(matches(isDisplayed()))
        visitorsBtn.perform(click())
        Thread.sleep(5000)


        //Revisa que este listado el album Poeta del pueblo
        val albumText =
            onView(allOf(withId(R.id.textView), withText("Poeta del pueblo"),
                isDisplayed()
            ))
        albumText.check(matches(isDisplayed()))
        albumText.perform(click())
        Thread.sleep(5000)

        //Revisa que este el titulo de album en el detalle
        val tituloText =
            onView(allOf(withId(R.id.albumTitleTextView), withText("Poeta del pueblo")
            ))
        tituloText.check(matches(isDisplayed()))

        val artistText =
            onView(allOf(withId(R.id.artistsTitleTextView), withText("Artistas")
            ))
        artistText.check(matches(isDisplayed()))
    }
}