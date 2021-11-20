package com.example.vinyls

//import android.R
import com.example.vinyls.R
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.vinyls.ui.activities.MainActivity
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HU03_InstrumentedTest {

    @Rule
    @JvmField
    var  mActivityTestRule: ActivityScenarioRule<UserSelectionActivity> =
        ActivityScenarioRule(UserSelectionActivity::class.java)

    @Test
    fun testHU03() {

        // Inicia con usuario "Usuario"
        val visitorsBtn =
            onView(allOf(withId(R.id.button4), withText("USUARIO"),
                //withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)),
                isDisplayed()
            ))
        visitorsBtn.check(matches(isDisplayed()))
        visitorsBtn.perform(click())
        Thread.sleep(5000)

        //Seleccionar Artistas
        val artistMenu =
            onView(allOf(
                withContentDescription(R.string.title_artists),
                isDisplayed()
            ))
        artistMenu.check(matches(isDisplayed()))
        artistMenu.perform(click())
        Thread.sleep(2000)

        //Revisa que este listado la banda Queen
        val bandText =
            onView(allOf(withId(R.id.artistNameTextView), withText("Queen"),
                isDisplayed()
            ))
        bandText.check(matches(isDisplayed()))

        //Revisa que este listado el musico Ruben Blades
        val musicianText =
            onView(allOf(withId(R.id.artistNameTextView), withText("Rubén Blades Bellido de Luna"),
                isDisplayed()
            ))
        musicianText.check(matches(isDisplayed()))



    }
}