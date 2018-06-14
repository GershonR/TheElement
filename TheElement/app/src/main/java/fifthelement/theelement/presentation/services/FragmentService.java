package fifthelement.theelement.presentation.services;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentService {
    AppCompatActivity application;

    public FragmentService(AppCompatActivity application) {
        this.application = application;
    }

    public void createFragment(int id, Fragment fragment) {
        FragmentManager fragmentManager = application.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(id, fragment);

        transaction.commit();
    }

    public long getApplicationHashCode() {
        return application.hashCode();
    }

}
