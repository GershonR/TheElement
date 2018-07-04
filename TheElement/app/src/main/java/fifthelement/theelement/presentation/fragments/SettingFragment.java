package fifthelement.theelement.presentation.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

import fifthelement.theelement.R;

public class LibraryFragment extends Fragment {

    private View view;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setContentView(R.layout.main);

        // Find the ListView resource.
        view = inflater.inflate(R.layout.library_fragment, container, false);
        mainListView = view.findViewById(R.id.library_view);


        // Create and populate a List of for the library.
        String[] options = new String[]{"Change Theme", "Delete Songs", "Hide album art notification "};
        ArrayList<String> libraryList = new ArrayList<String>();
        libraryList.addAll(Arrays.asList(options));

        // Create ArrayAdapter using the library list.
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, libraryList);


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
        return view;
    }
}