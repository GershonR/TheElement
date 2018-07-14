package fifthelement.theelement.presentation.services;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.presentation.fragments.HomeFragment;
import fifthelement.theelement.presentation.fragments.SettingFragment;
import fifthelement.theelement.presentation.fragments.PlayerStatsFragment;
import fifthelement.theelement.presentation.fragments.SearchFragment;
import fifthelement.theelement.presentation.fragments.SongListFragment;
import fifthelement.theelement.presentation.fragments.PlaylistListFragment;

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
        switch(menuItem.getItemId()) {
            case R.id.home_page:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.playlist_list:
                fragmentClass = PlaylistListFragment.class;
                break;
            case R.id.song_list:
                Services.getSongListService().setCurrentSongsList(Services.getSongService().getSongs());
                fragmentClass = SongListFragment.class;
                break;
            case R.id.search_view_fragment:
                fragmentClass = SearchFragment.class;
                break;
            default:
                fragmentClass = SettingFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        Helpers.getFragmentHelper(application).createFragment(R.id.flContent, fragment);

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
