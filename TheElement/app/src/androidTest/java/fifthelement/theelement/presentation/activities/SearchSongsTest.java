package fifthelement.theelement.presentation.activities;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import fifthelement.theelement.R;
import fifthelement.theelement.presentation.activities.TestHelpers.AndroidTestHelpers;

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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class SearchSongsTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void searchSongsTest(){
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
                        4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        AndroidTestHelpers.childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        AndroidTestHelpers.childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        AndroidTestHelpers.childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        AndroidTestHelpers.childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("adventure"), closeSoftKeyboard());

        ViewInteraction textView = onView(
                allOf(withId(R.id.primary_string), withText("Adventure of a Lifetime"),
                        AndroidTestHelpers.childAtPosition(
                                AndroidTestHelpers.childAtPosition(
                                        withId(R.id.search_song_list_view_item),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Adventure of a Lifetime")));
    }
}
