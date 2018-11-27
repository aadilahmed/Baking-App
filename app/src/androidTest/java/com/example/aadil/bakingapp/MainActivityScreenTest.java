package com.example.aadil.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    private static final String PIE_NAME = "Nutella Pie";
    private static final String BROWNIE_NAME = "Brownies";
    private static final String CAKE_NAME = "Yellow Cake";
    private static final String CHEESECAKE_NAME = "Cheesecake";

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickGridItem_CheckPieTitle() {
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_detail_title)).check(matches(withText(PIE_NAME)));
    }

    @Test
    public void clickGridItem_CheckBrownieTitle() {
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.recipe_detail_title)).check(matches(withText(BROWNIE_NAME)));
    }

    @Test
    public void clickGridItem_CheckCakeTitle() {
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.recipe_detail_title)).check(matches(withText(CAKE_NAME)));
    }

    @Test
    public void clickGridItem_CheckCheesecakeTitle() {
        onView(withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.recipe_detail_title)).check(matches(withText(CHEESECAKE_NAME)));
    }

}