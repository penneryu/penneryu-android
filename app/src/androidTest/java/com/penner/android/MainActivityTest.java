package com.penner.android;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by penneryu on 16/2/1.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
    }

    @Test
    public void fabClick() {
        Espresso.onView(ViewMatchers.withId(R.id.fab))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
