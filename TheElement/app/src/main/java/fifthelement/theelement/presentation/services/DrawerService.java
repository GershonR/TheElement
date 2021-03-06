package fifthelement.theelement.presentation.services;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.presentation.fragments.NowPlayingFragment;
import fifthelement.theelement.presentation.fragments.PlaylistListFragment;
import fifthelement.theelement.presentation.fragments.SearchFragment;
import fifthelement.theelement.presentation.fragments.SettingFragment;
import fifthelement.theelement.presentation.fragments.SongListFragment;

public class DrawerService {
    AppCompatActivity application;
    private NavigationView nvDrawer;
    private DrawerLayout mDrawer;

    private static final String LOG_TAG = "DrawerService";

    public DrawerService(AppCompatActivity application) {
        this.application = application;
        nvDrawer = application.findViewById(R.id.nvView);
        mDrawer = application.findViewById(R.id.drawer_layout);
        setupDrawerContent(nvDrawer);
    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        String tag = "";
        switch(menuItem.getItemId()) {
            case R.id.home_page:
                fragmentClass = NowPlayingFragment.class;
                tag = "NowPlayingFragment";
                break;
            case R.id.playlist_list:
                fragmentClass = PlaylistListFragment.class;
                tag = "PlayList";
                break;
            case R.id.song_list:
                fragmentClass = SongListFragment.class;
                tag = "SongList";
                break;
            case R.id.search_view_fragment:
                fragmentClass = SearchFragment.class;
                tag = "Search";
                break;
            default:
                fragmentClass = SettingFragment.class;
                tag = "Settings";
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        Helpers.getFragmentHelper(application).createFragment(R.id.flContent, fragment, tag);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        application.setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public DrawerLayout getmDrawer() {
        return mDrawer;
    }

    public long getApplicationHashCode() {
        return application.hashCode();
    }

}
