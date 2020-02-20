package com.vimosanan.weatherandroidapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.vimosanan.weatherandroidapplication.ui.main.WeatherActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Rule
    @JvmField
    val rule: ActivityTestRule<WeatherActivity> = ActivityTestRule(WeatherActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vimosanan.weatherandroidapplication", appContext.packageName)
    }

    @Test
    fun user_can_enter_city(){
        onView(withId(R.id.edtTxtSearch)).perform(typeText("Colombo"))
    }

    @Test
    fun user_can_search(){
        onView(withId(R.id.edtTxtSearch)).perform(typeText("Colombo"))

        onView(withId(R.id.btnSearch)).perform(click())

        onView(withId(R.id.txtInfo)).check(matches(withText("loading data from network")))
    }
}
