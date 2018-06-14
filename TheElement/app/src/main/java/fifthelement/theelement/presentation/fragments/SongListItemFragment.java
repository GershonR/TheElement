package fifthelement.theelement.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fifthelement.theelement.R;
import fifthelement.theelement.presentation.MainActivity;


public class SongListItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button button = getActivity().findViewById(R.id.popup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked Button");
                PopupMenu popup = new PopupMenu(getActivity(), v);
                getActivity().getMenuInflater().inflate(R.menu.song_list_item_menu, popup.getMenu());
                popup.show();
            }
        });
        return inflater.inflate(R.layout.fragment_song_list_item, container, false);
    }

}
