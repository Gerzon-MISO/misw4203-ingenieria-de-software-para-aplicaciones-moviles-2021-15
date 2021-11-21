package com.example.vinyls

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID


@LargeTest
@RunWith(AndroidJUnit4::class)
class HU08Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(UserSelectionActivity::class.java)

    @Test
    fun validateEmptyFieldsWhenCreateTrack() {

        // Start session as User
        onView(
            allOf(
                withId(R.id.button4),
                withText("Usuario"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        ).perform(click())
        Thread.sleep(5000)

        // Select an Album
        onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        ).perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        // Click in add track button
        onView(
            allOf(
                withId(R.id.agregar_cancion_but),
                withText("Agregar canción"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        ).perform(scrollTo(), click())

        // CLick in save track button
        onView(
            allOf(
                withId(R.id.saveButton),
                withText("Guardar"),
                childAtPosition(
                    allOf(
                        withId(R.id.formConstraintLayout),
                        childAtPosition(
                            withId(R.id.mainConstraintLayout),
                            1
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        ).perform(click())

        // Check text error name EditText
        onView(
            allOf(
                withId(R.id.trackNameEditText),
                isDisplayed()
            )
        ).check(matches(hasErrorText("El nombre es requerido!")))

        // Check text error minutes EditText
        onView(
            allOf(
                withId(R.id.minutesDurationEditText),
                isDisplayed()
            )
        ).check(matches(hasErrorText("Los minutos son requeridos!")))

        // Check text error seconds EditText
        onView(
            allOf(
                withId(R.id.secondsDurationEditText),
                isDisplayed()
            )
        ).check(matches(hasErrorText("Los segundos son requeridos!")))
    }

    @Test
    fun createTrackSuccessfully() {

        // Start session as User
        onView(
            allOf(
                withId(R.id.button4),
                withText("Usuario"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        ).perform(click())
        Thread.sleep(5000)

        // Select an Album
        val albumRandom = (0..3).random()
        onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        ).perform(actionOnItemAtPosition<ViewHolder>(albumRandom, click()))

        // Click in add track button
        onView(
            allOf(
                withId(R.id.agregar_cancion_but),
                withText("Agregar canción"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        ).perform(scrollTo(), click())

        val nameRandom = UUID.randomUUID().toString()
        val durationMinutes = (1..5).random()
        val durationSeconds = (1..60).random()

        // Ingress track name
        onView(
            allOf(
                withId(R.id.trackNameEditText),
                isDisplayed()
            )
        ).perform(replaceText(nameRandom), closeSoftKeyboard())

        // Ingress track duration (minutes)
        onView(
            allOf(
                withId(R.id.minutesDurationEditText),
                isDisplayed()
            )
        ).perform(replaceText(durationMinutes.toString()), closeSoftKeyboard())

        // Ingress track duration (seconds)
        onView(
            allOf(
                withId(R.id.secondsDurationEditText),
                isDisplayed()
            )
        ).perform(replaceText(durationSeconds.toString()), closeSoftKeyboard())

        // CLick in save track button
        onView(
            allOf(
                withId(R.id.saveButton),
                withText("Guardar"),
                childAtPosition(
                    allOf(
                        withId(R.id.formConstraintLayout),
                        childAtPosition(
                            withId(R.id.mainConstraintLayout),
                            1
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        ).perform(click())
        Thread.sleep(3000)
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
