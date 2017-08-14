package io.github.apollozhu.lottery.utils

import java.util.prefs.PreferenceChangeEvent

interface PreferenceLoading {
    fun loadPreferences(ignored: PreferenceChangeEvent?)

    fun loadPreferences() = loadPreferences(null)
}
