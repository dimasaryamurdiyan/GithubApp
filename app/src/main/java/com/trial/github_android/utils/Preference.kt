package com.trial.github_android.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.trial.github_android.di.PreferenceInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preference @Inject constructor(
    @ApplicationContext context: Context,
    @PreferenceInfo private val prefFileName: String
): PreferenceImpl {
    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun setTheme(state: Boolean) {
        mPrefs.edit { putBoolean("theme", state) }
    }

    override fun getTheme(): Boolean? {
        return mPrefs.getBoolean("theme", false)
    }
}