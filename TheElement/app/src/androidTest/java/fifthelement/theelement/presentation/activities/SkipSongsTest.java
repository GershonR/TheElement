package fifthelement.theelement.presentation.activities;


import android.support.test.espresso.DataInteraction;
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
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fifthelement.theelement.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SkipSongsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void skipSongsTest() {
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

        DataInteraction frameLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.song_list_view),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        frameLayout.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_next),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Classical Music", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Classical Music"));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.button_prev),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

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
