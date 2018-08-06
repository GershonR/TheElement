package fifthelement.theelement.presentation.helpers;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import fifthelement.theelement.presentation.activities.Delagate;

public class FragmentHelper {
    AppCompatActivity application;

    public FragmentHelper(AppCompatActivity application) {
        this.application = application;
    }

    public void createFragment(int id, Fragment fragment, String tag) {
        KeyguardManager myKM = (KeyguardManager) application.getSystemService(Context.KEYGUARD_SERVICE);
        PowerManager pm = (PowerManager)application.getSystemService(Context.POWER_SERVICE);

        InputMethodManager inputManager = (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(application.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        FragmentManager fragmentManager = application.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(id, fragment, tag);

        if(myKM.inKeyguardRestrictedInputMode() || !pm.isInteractive() || !Delagate.mainActivity.isVisible()) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    public long getApplicationHashCode() {
        return application.hashCode();
    }

}
