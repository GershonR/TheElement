package fifthelement.theelement.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fifthelement.theelement.R;

public class HomeFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Im in a Home Fragment Page");
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        textView = view.findViewById(R.id.home_page_example_text);
        textView.setText("Some Text In The Home Page");
        return view;

    }
}
