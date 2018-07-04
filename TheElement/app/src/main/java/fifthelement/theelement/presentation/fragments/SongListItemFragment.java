package fifthelement.theelement.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fifthelement.theelement.R;


public class SongListItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_list_item, container, false);
//        TextView songName = (TextView) view.findViewById(R.id.song_name_list);
//        songName.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

}
