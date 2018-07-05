package fifthelement.theelement.presentation.util;

import android.app.Activity;
import android.content.Intent;

import fifthelement.theelement.R;

public class ThemeUtil {
        private static int sTheme;
        public final static int THEME_DEFAULT = 0;
        public final static int THEME_GREEN = 1;
        public final static int THEME_DARK_BLUE = 2;
        /**
         * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
         */
        public static void changeToTheme(Activity activity, int theme)
        {
            sTheme = theme;
            activity.finish();
            activity.startActivity(new Intent(activity, activity.getClass()));
        }
        /** Set the theme of the activity, according to the configuration. */
        public static void onActivityCreateSetTheme(Activity activity)
        {
            switch (sTheme)
            {
                default:
                case THEME_DEFAULT:
                    activity.setTheme(R.style.AppTheme);
                    break;
                case THEME_GREEN:
                    activity.setTheme(R.style.GreenTheme);
                    break;
                case THEME_DARK_BLUE:
                    activity.setTheme(R.style.DarkBlueTheme);
                    break;
            }
        }
    }
