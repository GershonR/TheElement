package fifthelement.theelement.presentation.activities;

import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fifthelement.theelement.R;
import fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

public class PlayMusicTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void playPausingTest() {
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
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        DataInteraction frameLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.song_list_view),
                        AndroidTestHelpers.childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        frameLayout.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Music Service Has To Be Playing", mActivityTestRule.getActivity().getMusicService().isPlaying());
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_play_pause),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Music Service Has To Be Not Playing", !mActivityTestRule.getActivity().getMusicService().isPlaying());
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.button_play_pause),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Music Service Has To Be Playing", mActivityTestRule.getActivity().getMusicService().isPlaying());
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

    }

    @Test
    public void seekTest() {
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
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        DataInteraction frameLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.song_list_view),
                        AndroidTestHelpers.childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        frameLayout.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);

        mActivityTestRule.getActivity().getMusicService().seekTo(1000);
        SystemClock.sleep(500);
        mActivityTestRule.getActivity().getMusicService().seekTo(50000);
        SystemClock.sleep(500);
        mActivityTestRule.getActivity().getMusicService().seekTo(100000);
        SystemClock.sleep(500);

    }

    @Test
    public void skipSongsTest() {
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
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        DataInteraction frameLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.song_list_view),
                        AndroidTestHelpers.childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)))
                .atPosition(0);
        frameLayout.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_next),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
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
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        Assert.assertTrue("Music Service Cannot Be Null", mActivityTestRule.getActivity().getMusicService() != null);
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
        Assert.assertTrue("Current Song Playing Not Adventure of a Lifetime", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying().getName().equals("Adventure of a Lifetime"));

    }

    @Test
    public void shuffleSongTest() {
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
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.shuffle), withText(" SHUFFLE"),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));

        SystemClock.sleep(500);

        appCompatButton.perform(click());

    }
}
