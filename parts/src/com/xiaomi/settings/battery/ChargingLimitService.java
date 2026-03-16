/*
 * Copyright (C) 2023-2024 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.battery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;

import androidx.preference.PreferenceManager;

public class ChargingLimitService extends Service {
    private BroadcastReceiver mBatteryReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mBatteryReceiver == null) {
            mBatteryReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                        updateChargingState(context, intent);
                    }
                }
            };
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(mBatteryReceiver, filter);
        }
        return START_STICKY;
    }

    private void updateChargingState(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isEnabled = prefs.getBoolean(BatteryUtils.PREF_CHARGING_CTRL, false);
        int limit = prefs.getInt(BatteryUtils.PREF_CHARGING_LIMIT, 80);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isPlugged = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                          status == BatteryManager.BATTERY_STATUS_FULL;

        if (isEnabled && isPlugged && level >= limit) {
            BatteryUtils.setChargingSuspend(true);
        } else {
            BatteryUtils.setChargingSuspend(false);
        }
    }

    @Override
    public void onDestroy() {
        if (mBatteryReceiver != null) {
            unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
