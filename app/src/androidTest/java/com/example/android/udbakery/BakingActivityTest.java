package com.example.android.udbakery;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/**
 * Created by SSubra27 on 8/30/17.
 */

@RunWith(AndroidJUnit4.class)
public class BakingActivityTest {
    public class RecyclerViewItemCountAssertion implements ViewAssertion {

        private Matcher<Integer> matcher;

        public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {

            this.matcher = matcher;

        }



        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {


            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;

            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            assertThat(adapter.getItemCount(), is(matcher));


        }
    }


    @Rule public ActivityTestRule<BakingActivity> mBakingActivityActivityTestRule
            = new ActivityTestRule<BakingActivity>(BakingActivity.class);

    @Test
    public void clickRecipeButton()
    {
        onView(withId(R.id.rv_recipe_card_layout)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


    }


    @Test
    public void countRecipeSteps()
    {

        onView(withId(R.id.rv_recipe_card_layout)).check(matches(allOf(isDisplayed()))).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));


        onView(withId(R.id.rv_steps_desc)).check(new RecyclerViewItemCountAssertion(is(13)));


    }
    @Test
    public void checkRecipeButtonText()
    {
        String itemElementText = mBakingActivityActivityTestRule.getActivity().getResources()
                .getString(R.string.item_element_text);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }
}
