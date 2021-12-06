package com.example.vinyls

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.vinyls.ui.activities.UserSelectionActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class HU07Test {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(UserSelectionActivity::class.java)

    @Test
    fun createAlbumSuccessfully() {

        // Start session as User
        Espresso.onView(
            allOf(
                withId(R.id.button3),
                withText("Visitante"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        ).perform(click())

        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Thread.sleep(1500)

        Espresso.onView(
            allOf(
                withId(R.id.agregarAlbumButt),
                withText("Agregar Album"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.core.widget.NestedScrollView")),
                        0
                    ),
                    1
                )
            )
        ).perform(click())
        Thread.sleep(3000)

        val nameRandom = UUID.randomUUID().toString()

        // Ingress album name
        Espresso.onView(
            allOf(
                withId(R.id.albumNameEditText),
                isDisplayed()
            )
        ).perform(replaceText(nameRandom), closeSoftKeyboard())

        // Ingress url cover
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.coverEditText),
                ViewMatchers.isDisplayed()
            )
        ).perform(
            ViewActions.replaceText("https://www.google.com/url?sa=i&url=https%3A%2F%2Ftheconversation.com%2Fweezers-cover-album-is-the-rock-band-honoring-or-exploiting-the-originals-110559&psig=AOvVaw15Wflksckh6qYQyCOzhUKY&ust=1638764747242000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCNi5wcjoy_QCFQAAAAAdAAAAABAD"),
            ViewActions.closeSoftKeyboard()
        )

        // Ingress description album.
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.descEditText),
                ViewMatchers.isDisplayed()
            )
        ).perform(
            replaceText("This is a Test"),
            closeSoftKeyboard()
        )

        // Select a genre
        val genreRandom = (0..3).random()
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.spinner1),
                childAtPosition(
                    allOf(
                        ViewMatchers.withId(R.id.genreTextField),
                        childAtPosition(
                            ViewMatchers.withId(R.id.mainConstraintLayout),
                            4
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(click())

        Espresso.onData(
            anything()
        ).atPosition(genreRandom).perform(click())


        // Select a label
        val labelRandom = (0..4).random()
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.spinner2),
                childAtPosition(
                    allOf(
                        ViewMatchers.withId(R.id.labelTextField),
                        childAtPosition(
                            ViewMatchers.withId(R.id.mainConstraintLayout),
                            5
                        )
                    ),
                    1
                ),
            )
        ).perform(click())

        Espresso.onData(
            anything()
        ).atPosition(labelRandom).perform(click())

        // Select date
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.dateEditText),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.dateTextField),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(click())

        val materialButton = Espresso.onView(
            allOf(
                ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("OK"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(click())

        // CLick in save album button
        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.albumSaveButton), ViewMatchers.withText("Guardar"),
                childAtPosition(
                    allOf(
                        ViewMatchers.withId(R.id.mainConstraintLayout),
                        childAtPosition(
                            ViewMatchers.withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    7
                ),
                ViewMatchers.isDisplayed()
            )
        ).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun  validateEmptyFieldsWhenCreateAlbum() {

        // Start session as User
        Espresso.onView(
            allOf(
                withId(R.id.button4),
                withText("Usuario"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                )
            )
        ).perform(click())
    /*
        Espresso.onView(
            withId(R.id.headerConstraintLayout)
        ).perform(repeatedlyUntil(swipeUp(),
            hasDescendant(withText("Agregar Album")),
            10))
        Thread.sleep(3000)

     */
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Espresso.onView(
            withId(R.id.nestedScrollView)
        ).perform(swipeUp())
        Thread.sleep(1500)

        // Click in add an Album button
        Espresso.onView(
            allOf(
                withId(R.id.agregarAlbumButt),
                withText("Agregar Album"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.core.widget.NestedScrollView")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        ).perform(click())
        Thread.sleep(1500)

        // CLick in save album button
        Espresso.onView(
            allOf(
                withId(R.id.albumSaveButton),
                withText("Guardar"),
                childAtPosition(
                    allOf(
                        withId(R.id.mainConstraintLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.FrameLayout")),
                            0
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        ).perform(click())

        // Check text error name EditText
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumNameEditText),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.hasErrorText("El nombre es requerido!")))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.coverEditText),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.hasErrorText("El url de la portada es requerida!")))

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.descEditText),
                ViewMatchers.isDisplayed()
            )
        ).check(ViewAssertions.matches(ViewMatchers.hasErrorText("La fecha es requerida!")))
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