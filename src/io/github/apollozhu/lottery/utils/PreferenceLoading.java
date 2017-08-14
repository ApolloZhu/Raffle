package io.github.apollozhu.lottery.utils;

import org.jetbrains.annotations.Nullable;

import java.util.prefs.PreferenceChangeEvent;

public interface PreferenceLoading {
    void loadPreferences(@Nullable PreferenceChangeEvent ignored);

    default void loadPreferences() {
        loadPreferences(null);
    }
}
