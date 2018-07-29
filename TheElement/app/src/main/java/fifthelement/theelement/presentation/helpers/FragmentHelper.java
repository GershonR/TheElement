package fifthelement.theelement.presentation.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentHelper {
    AppCompatActivity application;

    public FragmentHelper(AppCompatActivity application) {
        this.application = application;
    }

    public void createFragment(int id, Fragment fragment, String tag) {
        FragmentManager fragmentManager = application.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(id, fragment, tag);

        transaction.commit();
    }

    public long getApplicationHashCode() {
        return application.hashCode();
    }

}
