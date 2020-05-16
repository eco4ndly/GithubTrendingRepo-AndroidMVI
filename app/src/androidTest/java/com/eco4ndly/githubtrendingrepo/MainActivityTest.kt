package com.eco4ndly.githubtrendingrepo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.eco4ndly.githubtrendingrepo.main.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A Sayan Porya code on 15/05/20
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity>
      = ActivityTestRule(MainActivity::class.java)

  @Test
  fun textChange_charCount_shouldBeSame() {
    val stringTobeTyped = "Hello there I am Jin Yang"

    onView(withId(R.id.et_text))
      .perform(typeText(stringTobeTyped), closeSoftKeyboard())

    onView(withId(R.id.tv_char_count))
      .check(matches(withText(stringTobeTyped.length.toString())))
  }

  @Test
  fun textChanges_onTextViewClicks() {
    onView(withId(R.id.test_txt))
      .perform(click())
    onView(withId(R.id.test_txt))
      .perform(click())
    onView(withId(R.id.test_txt))
      .perform(click())

    onView(withId(R.id.test_txt))
      .check(matches(withText("Count 2")))
  }

}