package com.sai.linq.wearable.activities

import android.os.Bundle
import android.preference.PreferenceActivity
import com.sai.linq.wearable.R


//https://stackoverflow.com/questions/48036941/is-it-possible-to-use-preferencefragmentcompat-on-android-wear-and-have-correct
class LinqSettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_screen)
    }

}
