package fifthelement.theelement.presentation.activities;

import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Song;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers.childAtPosition;
import static fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers.first;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

public class MusicLibraryTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void playSongFromSongsList() {
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
        Assert.assertTrue("Music Service Has To Be Playing", mActivityTestRule.getActivity().getMusicService().isPlaying());
        Assert.assertTrue("Current Song Playing Cannot Be Null", mActivityTestRule.getActivity().getMusicService().getCurrentSongPlaying() != null);
    }

    @Test
    public void searchSongsTest(){
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
                        4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("adventure"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.primary_string), withText("Adventure of a Lifetime"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.search_song_list_view_item),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Adventure of a Lifetime")));
    }

    @Test
    public void deleteSongTest() {
        Assert.assertTrue("Song Service Cannot Be Null", mActivityTestRule.getActivity().getSongService() != null);
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

        Assert.assertTrue("Song Size Is Not 3", mActivityTestRule.getActivity().getSongService().getSongs().size() == 3);
        SystemClock.sleep(500);

        deleteSongRestore();
    }

    private void deleteSongRestore() {
        Song song = new Song("Adventure of a Lifetime", "android.resource://fifthelement.theelement/raw/coldplay_adventure_of_a_lifetime");
        song.setAuthor(new Author("Coldplay"));
        song.setGenre("Pop");
        song.setRating(3.5);
        try {
            mActivityTestRule.getActivity().getSongService().insertSong(song);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO write test to make sure music library is organized alphabetically
}
