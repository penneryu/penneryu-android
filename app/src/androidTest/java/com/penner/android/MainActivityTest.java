package com.penner.android;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by penneryu on 16/2/1.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void viewPager() {
        onView(withId(R.id.main_viewpager)).perform(swipeLeft());
    }

    @Test
    public void fabClick() {
        onView(withId(R.id.fab))
                .perform(click())
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void recyclerViewClick() {
        onView(allOf(withId(R.id.main_recycler), FirstViewMatcher.firstView()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    public static class FirstViewMatcher extends BaseMatcher<View> {

        public static boolean matchedBefore = false;

        public FirstViewMatcher() {
            matchedBefore = false;
        }

        @Override
        public boolean matches(Object o) {
            if (matchedBefore) {
                return false;
            } else {
                matchedBefore = true;
                return true;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(" is the first view that comes along ");
        }

        @Factory
        public static <T> Matcher<View> firstView() {
            return new FirstViewMatcher();
        }
    }
}
