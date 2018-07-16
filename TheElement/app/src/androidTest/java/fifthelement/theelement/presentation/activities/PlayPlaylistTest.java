package fifthelement.theelement.presentation.activities;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import fifthelement.theelement.R;
import fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class PlayPlaylistTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void playPlaylistTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        AndroidTestHelpers.childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        AndroidTestHelpers.childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(AndroidTestHelpers.childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                AndroidTestHelpers.childAtPosition(
                                        withId(R.id.nvView),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        onView(AndroidTestHelpers.withIndex(withId(R.id.popup_button),0)).perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Play"),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        // The important part of the test
        String toCheck = "Now Playing:";
        AndroidTestHelpers.toastStringChecker(toCheck, mActivityTestRule);
    }
}
