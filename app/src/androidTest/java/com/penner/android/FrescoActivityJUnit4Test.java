package com.penner.android;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by penneryu on 16/2/1.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FrescoActivityJUnit4Test {

    @Rule
    public ActivityTestRule<FrescoActivity> mActivityRule = new ActivityTestRule<>(
            FrescoActivity.class);

    @Test
    public void paletteClick() {
        Espresso.onView(ViewMatchers.withId(R.id.fresco_palette))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
