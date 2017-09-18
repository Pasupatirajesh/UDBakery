package com.example.android.udbakery;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BakingActivityTestRecording {




    @Rule
    public ActivityTestRule<BakingActivity> mActivityTestRule = new ActivityTestRule<>(BakingActivity.class);


    @Test
    public void bakingActivityTestRecording() {
        ViewInteraction button = onView(
                allOf(withId(R.id.bt_recipe_one),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipe_card_layout),
                                        0),
                                0),
                        isDisplayed()));
        button.check(matches(isClickable()));

        onView(withId(R.id.rv_recipe_card_layout)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.rv_steps_desc)).check(matches(hasDescendant(withText("Recipe Introduction"))));

        onView(withId(R.id.rv_steps_desc)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(allOf(withId(R.id.vv_simple), isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
