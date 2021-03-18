package awais.instagrabber.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatDelegate;

import static awais.instagrabber.fragments.settings.PreferenceKeys.PREF_ENABLE_DM_AUTO_REFRESH;
import static awais.instagrabber.fragments.settings.PreferenceKeys.PREF_ENABLE_DM_AUTO_REFRESH_FREQ_NUMBER;
import static awais.instagrabber.fragments.settings.PreferenceKeys.PREF_ENABLE_DM_AUTO_REFRESH_FREQ_UNIT;
import static awais.instagrabber.fragments.settings.PreferenceKeys.PREF_ENABLE_DM_NOTIFICATIONS;
import static awais.instagrabber.utils.Constants.APP_LANGUAGE;
import static awais.instagrabber.utils.Constants.APP_THEME;
import static awais.instagrabber.utils.Constants.APP_UA;
import static awais.instagrabber.utils.Constants.APP_UA_CODE;
import static awais.instagrabber.utils.Constants.AUTOPLAY_VIDEOS;
import static awais.instagrabber.utils.Constants.BROWSER_UA;
import static awais.instagrabber.utils.Constants.BROWSER_UA_CODE;
import static awais.instagrabber.utils.Constants.CHECK_ACTIVITY;
import static awais.instagrabber.utils.Constants.CHECK_UPDATES;
import static awais.instagrabber.utils.Constants.COOKIE;
import static awais.instagrabber.utils.Constants.CUSTOM_DATE_TIME_FORMAT;
import static awais.instagrabber.utils.Constants.CUSTOM_DATE_TIME_FORMAT_ENABLED;
import static awais.instagrabber.utils.Constants.DATE_TIME_FORMAT;
import static awais.instagrabber.utils.Constants.DATE_TIME_SELECTION;
import static awais.instagrabber.utils.Constants.DEFAULT_TAB;
import static awais.instagrabber.utils.Constants.DEVICE_UUID;
import static awais.instagrabber.utils.Constants.DM_MARK_AS_SEEN;
import static awais.instagrabber.utils.Constants.DOWNLOAD_USER_FOLDER;
import static awais.instagrabber.utils.Constants.FLAG_SECURE;
import static awais.instagrabber.utils.Constants.FOLDER_PATH;
import static awais.instagrabber.utils.Constants.FOLDER_SAVE_TO;
import static awais.instagrabber.utils.Constants.MARK_AS_SEEN;
import static awais.instagrabber.utils.Constants.MUTED_VIDEOS;
import static awais.instagrabber.utils.Constants.PREF_DARK_THEME;
import static awais.instagrabber.utils.Constants.PREF_EMOJI_VARIANTS;
import static awais.instagrabber.utils.Constants.PREF_HASHTAG_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_LIGHT_THEME;
import static awais.instagrabber.utils.Constants.PREF_LIKED_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_LOCATION_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_PROFILE_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_REACTIONS;
import static awais.instagrabber.utils.Constants.PREF_SAVED_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_TAGGED_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREF_TOPIC_POSTS_LAYOUT;
import static awais.instagrabber.utils.Constants.PREV_INSTALL_VERSION;
import static awais.instagrabber.utils.Constants.SHOW_CAPTIONS;
import static awais.instagrabber.utils.Constants.SHOW_QUICK_ACCESS_DIALOG;
import static awais.instagrabber.utils.Constants.SKIPPED_VERSION;
import static awais.instagrabber.utils.Constants.STORY_SORT;
import static awais.instagrabber.utils.Constants.SWAP_DATE_TIME_FORMAT_ENABLED;

public final class SettingsHelper {
    private final SharedPreferences sharedPreferences;

    public SettingsHelper(@NonNull final Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @NonNull
    public String getString(@StringSettings final String key) {
        final String stringDefault = getStringDefault(key);
        if (sharedPreferences != null) return sharedPreferences.getString(key, stringDefault);
        return stringDefault;
    }

    public int getInteger(@IntegerSettings final String key) {
        final int integerDefault = getIntegerDefault(key);
        if (sharedPreferences != null) return sharedPreferences.getInt(key, integerDefault);
        return integerDefault;
    }

    public boolean getBoolean(@BooleanSettings final String key) {
        if (sharedPreferences != null) return sharedPreferences.getBoolean(key, false);
        return false;
    }

    @NonNull
    private String getStringDefault(@StringSettings final String key) {
        if (DATE_TIME_FORMAT.equals(key))
            return "hh:mm:ss a 'on' dd-MM-yyyy";
        if (DATE_TIME_SELECTION.equals(key))
            return "0;3;0";
        return "";
    }

    private int getIntegerDefault(@IntegerSettings final String key) {
        if (APP_THEME.equals(key)) return getThemeCode(true);
        if (PREV_INSTALL_VERSION.equals(key) || APP_UA_CODE.equals(key) || BROWSER_UA_CODE.equals(key)) return -1;
        return 0;
    }

    public int getThemeCode(final boolean fromHelper) {
        int themeCode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

        if (!fromHelper && sharedPreferences != null) {
            themeCode = Integer.parseInt(sharedPreferences.getString(APP_THEME, String.valueOf(themeCode)));
            switch (themeCode) {
                case 1:
                    themeCode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
                    break;
                case 3:
                    themeCode = AppCompatDelegate.MODE_NIGHT_NO;
                    break;
                case 0:
                    themeCode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                    break;
            }
        }

        if (themeCode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM && Build.VERSION.SDK_INT < 29)
            themeCode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;

        return themeCode;
    }

    public void putString(@StringSettings final String key, final String val) {
        if (sharedPreferences != null) sharedPreferences.edit().putString(key, val).apply();
    }

    public void putInteger(@IntegerSettings final String key, final int val) {
        if (sharedPreferences != null) sharedPreferences.edit().putInt(key, val).apply();
    }

    public void putBoolean(@BooleanSettings final String key, final boolean val) {
        if (sharedPreferences != null) sharedPreferences.edit().putBoolean(key, val).apply();
    }

    @StringDef(
            {APP_LANGUAGE, APP_THEME, APP_UA, BROWSER_UA, COOKIE, FOLDER_PATH, DATE_TIME_FORMAT, DATE_TIME_SELECTION,
                    CUSTOM_DATE_TIME_FORMAT, DEVICE_UUID, SKIPPED_VERSION, DEFAULT_TAB, PREF_DARK_THEME, PREF_LIGHT_THEME,
                    PREF_POSTS_LAYOUT, PREF_PROFILE_POSTS_LAYOUT, PREF_TOPIC_POSTS_LAYOUT, PREF_HASHTAG_POSTS_LAYOUT,
                    PREF_LOCATION_POSTS_LAYOUT, PREF_LIKED_POSTS_LAYOUT, PREF_TAGGED_POSTS_LAYOUT, PREF_SAVED_POSTS_LAYOUT,
                    STORY_SORT, PREF_EMOJI_VARIANTS, PREF_REACTIONS, PREF_ENABLE_DM_AUTO_REFRESH_FREQ_UNIT})
    public @interface StringSettings {}

    @StringDef({DOWNLOAD_USER_FOLDER, FOLDER_SAVE_TO, AUTOPLAY_VIDEOS, SHOW_QUICK_ACCESS_DIALOG, MUTED_VIDEOS,
                       SHOW_CAPTIONS, CUSTOM_DATE_TIME_FORMAT_ENABLED, MARK_AS_SEEN, DM_MARK_AS_SEEN, CHECK_ACTIVITY,
                       CHECK_UPDATES, SWAP_DATE_TIME_FORMAT_ENABLED, PREF_ENABLE_DM_NOTIFICATIONS, PREF_ENABLE_DM_AUTO_REFRESH,
                       FLAG_SECURE})
    public @interface BooleanSettings {}

    @StringDef({PREV_INSTALL_VERSION, BROWSER_UA_CODE, APP_UA_CODE, PREF_ENABLE_DM_AUTO_REFRESH_FREQ_NUMBER})
    public @interface IntegerSettings {}
}