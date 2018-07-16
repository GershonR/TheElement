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
import fifthelement.theelement.presentation.constants.SettingsConstants;
import fifthelement.theelement.presentation.util.ThemeUtil;

public class SettingFragment extends Fragment {

    private View view;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Find the ListView resource.
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        mainListView = view.findViewById(R.id.library_view);

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