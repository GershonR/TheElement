package fifthelement.theelement.presentation.activities;


import android.support.test.espresso.ViewInteraction;
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

import fifthelement.theelement.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OrganizeMusicCollectionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void organizeMusicCollectionTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nvView),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.primary_string), withText("Adventure of a Lifetime"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.song_list_view),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Adventure of a Lifetime")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.primary_string), withText("Classical Music"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.song_list_view),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Classical Music")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.primary_string), withText("Hall of Fame"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.song_list_view),
                                        2),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Hall of Fame")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.primary_string), withText("This Is America"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.song_list_view),
                                        3),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("This Is America")));

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
