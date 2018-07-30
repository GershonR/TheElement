package fifthelement.theelement.presentation.activities;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fifthelement.theelement.BuildConfig;
import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.business.util.SongMetaUtil;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.persistence.hsqldb.PersistenceException;
import fifthelement.theelement.presentation.adapters.CompactSongsListAdapter;
import fifthelement.theelement.presentation.adapters.PlaylistListAdapter;
import fifthelement.theelement.presentation.fragments.NowPlaying;
import fifthelement.theelement.presentation.fragments.PlaylistListFragment;
import fifthelement.theelement.presentation.fragments.SeekerFragment;
import fifthelement.theelement.presentation.fragments.SongListFragment;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.services.MusicService.MusicBinder;
import fifthelement.theelement.presentation.util.DatabaseUtil;
import fifthelement.theelement.presentation.util.ThemeUtil;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private SongService songService;
    private SongListService songListService;
    private MusicService musicService;
    private PlaylistService playlistService;
    private Intent playIntent;
    private boolean musicBound = false;
    private static final String LOG_TAG = "MainActivity";

    public SongService getSongService() {
        return songService;
    }
    public MusicService getMusicService(){
        return musicService;
    }
    public PlaylistService getPlaylistService(){
        return playlistService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create hamburger icon
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        setActionBarTitleAsMarquee();

        mDrawer = Services.getDrawerService(this).getmDrawer();
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        TextView version = (TextView)findViewById(R.id.footer_item);

        version.setText("Version: " + versionName + versionCode);
        DatabaseUtil.copyDatabaseToDevice(this);

        songService = Services.getSongService();
        songListService = Services.getSongListService();
        playlistService = new PlaylistService();
        //Sets current song list to the list of all songs in app
        songListService.setCurrentSongsList(songService.getSongs());

        Delagate.mainActivity = this;
    }

    private void setActionBarTitleAsMarquee(){
        // Get Action Bar's title

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.getChildAt(0);

        // Set the ellipsize mode to MARQUEE and make it scroll only once
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setMarqueeRepeatLimit(-1);

        // In order to start strolling, it has to be focusable and focused
        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        title.requestFocus();
    }

    private void createDefaultPage() {
        SongListFragment songListFragment = new SongListFragment();
        Helpers.getFragmentHelper(this).createFragment(R.id.flContent, songListFragment, "SongList");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_song:
                Intent myIntent = new Intent(MainActivity.this, AddMusicActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            case android.R.id.home:
                hideMenuItems();
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.new_playlist:
                Helpers.getPlaylistHelper().newPlaylistDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideMenuItems() {
        NavigationView nvDrawer = findViewById(R.id.nvView);
        Menu navMenu = nvDrawer.getMenu();
        MusicService musicService = Services.getMusicService();
        if(musicService == null || musicService.getCurrentSongPlaying() == null) {
            navMenu.findItem(R.id.home_page).setVisible(false);
        } else {
            navMenu.findItem(R.id.home_page).setVisible(true);
        }
    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            musicService = binder.getService();
            Services.setMusicService(musicService);
            createDefaultPage();
            musicBound = true;
            createSeeker();
            musicService.reset();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void createSeeker() {
        SeekerFragment seeker = new SeekerFragment();//create the fragment instance
        Helpers.getFragmentHelper(this).createFragment(R.id.music_seeker, seeker, "Seeker");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent == null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        musicService.stopNotificationService();
        stopService(playIntent);
        unbindService(musicConnection);
        musicService = null;
        DatabaseUtil.killDB();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
