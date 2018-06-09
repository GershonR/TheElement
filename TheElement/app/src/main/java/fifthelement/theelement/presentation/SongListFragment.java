package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import fifthelement.theelement.R;

public class SongListFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Im in a SongList Fragment Page");
        View view = inflater.inflate(R.layout.song_list_fragment, container, false);
        textView = view.findViewById(R.id.song_list_text_example);
        textView.setText("Example Text Here");
        return view;
    }
}
