package fifthelement.theelement.application;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import fifthelement.theelement.presentation.helpers.FragmentHelper;
import fifthelement.theelement.presentation.helpers.ToastHelper;

public class Helpers {

    private static FragmentHelper fragmentHelper = null;
    private static ToastHelper toastHelper = null;


    public static synchronized ToastHelper getToastHelper(Context context) {

        if (toastHelper == null) {
            toastHelper = new ToastHelper(context);
        }

        return toastHelper;
    }

    public static synchronized FragmentHelper getFragmentHelper(AppCompatActivity appCompatActivity) {

        if(fragmentHelper == null || appCompatActivity.hashCode() != fragmentHelper.getApplicationHashCode())
            fragmentHelper = new FragmentHelper(appCompatActivity);

        return fragmentHelper;
    }
}
