package com.example.android.udbakery;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by SSubra27 on 8/30/17.
 */

@RunWith(AndroidJUnit4.class)
public class BakingActivityTest {

    @Rule public ActivityTestRule<BakingActivity> mBakingActivityActivityTestRule
            = new ActivityTestRule<BakingActivity>(BakingActivity.class);

    @Test
    public void clickRecipeButton()
    {
        onView(withId(R.id.bt_recipe_one)).perform(click());
//                .check(matches(withText("Nutella Pie")));

    }
}
