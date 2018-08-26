package fifthelement.theelement.presentation.helpers;


import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.presentation.activities.MainActivity;

public class BackHelper {

    public static void setupBack(MainActivity activity, Fragment target, String tag) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helpers.getFragmentHelper(activity).createFragment(R.id.flContent, target, tag);
                activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
                toolbar.setNavigationOnClickListener(null);
                activity.setSupportActionBar(toolbar);
            }
        });
    }

}
