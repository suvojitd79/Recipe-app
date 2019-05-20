package com.example.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity>
            activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test1(){

        Espresso.onView(withId(R.id.toolBar))
                .check(matches(isDisplayed()));

    }


    @Test
    public void test2(){

        Espresso.onView(withId(R.id.shareApp))
                .check(matches(isDisplayed()));

    }

}