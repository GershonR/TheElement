package fifthelement.theelement.presentation.activities;

import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers.childAtPosition;
import static fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers.first;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class CustomizeTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkStatsPage() {
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
                        5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.library_view),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(3);
        appCompatTextView.perform(click());
    }

    @Test
    public void playSongWithSongPlayedCheck() {
        checkStatsPage();
        int oldMostPlayed = mActivityTestRule.getActivity().getSongService().getTotalSongPlays();
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
        frameLayout.perform(click());
        checkStatsPage();
        int newMostPlayed = mActivityTestRule.getActivity().getSongService().getTotalSongPlays();
        Assert.assertTrue(oldMostPlayed+2 == newMostPlayed);
    }

    @Test
    public void skipSongWithSongPlayedCheck() {
        checkStatsPage();
        int oldMostPlayed = mActivityTestRule.getActivity().getSongService().getTotalSongPlays();
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
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_next),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
        appCompatImageButton2.perform(click());
        checkStatsPage();
        int newMostPlayed = mActivityTestRule.getActivity().getSongService().getTotalSongPlays();
        Assert.assertTrue(oldMostPlayed+1 == newMostPlayed);
    }

    @Test
    public void deleteSong() {
        checkStatsPage();
        int totalSongsOwned = mActivityTestRule.getActivity().getSongService().getSortedSongListByMostPlayed().size();
        Song oldMostPlayedSong = mActivityTestRule.getActivity().getSongService().getMostPlayedSong();


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

        ViewInteraction appCompatImageButton2 = onView(
                allOf(first(allOf(withId(R.id.popup_button),
                        isDisplayed()))));
        appCompatImageButton2.perform(click());

        Assert.assertTrue("Song Size Is Not 4", mActivityTestRule.getActivity().getSongService().getSongs().size() == 4);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Delete Song"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        checkStatsPage();
        SystemClock.sleep(1000);
        int newTotalSongsOwned = mActivityTestRule.getActivity().getSongService().getSortedSongListByMostPlayed().size();
        Song newMostPlayedSong = mActivityTestRule.getActivity().getSongService().getMostPlayedSong();
        Assert.assertTrue(totalSongsOwned-1 == newTotalSongsOwned);
        Assert.assertNotNull("No song to assert with. Empty list?",oldMostPlayedSong);
        Assert.assertNotEquals(oldMostPlayedSong, newMostPlayedSong);
    }
}
