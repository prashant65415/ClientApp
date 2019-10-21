package com.example.clientapp;

import android.app.Instrumentation;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class); //Create Activity

    //activity instance
    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity(); //Initialise activity instance
    }

    @Test
    public void testClientServerAppFlow() throws InterruptedException {

        //Write ho in edit text
        onView(withId(R.id.editText)).perform(typeText("hi"));

        //Close soft keyboard
        onView(withId(R.id.editText)).perform(closeSoftKeyboard());

        //Click submit button
        onView(withId(R.id.button)).perform(click());

        //wait for activity response and compare the value in assert
        while(true)
        {
            if(mActivity.response!=null) {
                assertEquals("hi", mActivity.response);
                break;
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null; //make activity instance null
    }
}
