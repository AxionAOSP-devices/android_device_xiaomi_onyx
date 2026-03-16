/*
 * Copyright (C) 2023-2024 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.battery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.xiaomi.settings.R;
import com.android.settingslib.widget.SettingsBasePreferenceFragment;

public class ChargingControlFragment extends SettingsBasePreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.charging_control_settings);

        findPreference(BatteryUtils.PREF_CHARGING_CTRL).setOnPreferenceChangeListener(this);
        findPreference(BatteryUtils.PREF_CHARGING_LIMIT).setOnPreferenceChangeListener(this);

        updateChargingLimitSummary();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Intent intent = new Intent(getContext(), ChargingLimitService.class);
        getContext().startServiceAsUser(intent, UserHandle.CURRENT);

        if (preference.getKey().equals(BatteryUtils.PREF_CHARGING_LIMIT)) {
            int value = (int) newValue;
            preference.setSummary(getString(R.string.charging_limit_summary, value));
        }

        return true;
    }

    private void updateChargingLimitSummary() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int limit = prefs.getInt(BatteryUtils.PREF_CHARGING_LIMIT, 80);
        findPreference(BatteryUtils.PREF_CHARGING_LIMIT)
                .setSummary(getString(R.string.charging_limit_summary, limit));
    }
}
