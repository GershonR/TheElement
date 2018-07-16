package fifthelement.theelement.presentation.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.presentation.activities.MainActivity;
import fifthelement.theelement.presentation.constants.SettingsConstants;
import fifthelement.theelement.presentation.util.ThemeUtil;

public class SettingFragment extends Fragment {

    private View view;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    public final static int THEME_HIGH_VAL = 2;
    public final static int DELETE_SONGS = 3;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Find the ListView resource.
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        mainListView = view.findViewById(R.id.library_view);


        // Create and populate a List of for the library.
        String[] options = new String[]{"Theme1", "Theme2", "Theme3", "Delete Songs"};
        ArrayList<String> libraryList = new ArrayList<String>();
        libraryList.addAll(Arrays.asList(SettingsConstants.SETTING_OPTIONS));

        // Create ArrayAdapter using the library list.
        listAdapter = new ArrayAdapter<>(getActivity(), R.layout.simplerow, libraryList);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    selectTheme();
                }
                if(position <= THEME_HIGH_VAL) {
                    ThemeUtil.changeToTheme(getActivity(), position);
                    System.out.println("Called: " + position);
                } else if(position == DELETE_SONGS){
                    SongService songService = Services.getSongService();
                    SongListService songListService = Services.getSongListService();
                    songService.clearAllSongs();
                    songListService.setCurrentSongsList(songService.getSongs());
                    songListService.setAllSongsList(songService.getSongs());
                    Services.getMusicService().reset();
                    Helpers.getToastHelper((getActivity()).getApplicationContext()).sendToast("Deleted all songs");
                }
            }
        });

        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
        return view;
    }

    private void selectTheme() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please choose a theme:")

                .setItems(SettingsConstants.THEMES, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int clicked) {
                        ThemeUtil.changeToTheme(getActivity(), clicked);
                    }
                });
        builder.create();
        builder.show();
    }
}