/*
 * Copyright (C) 2025 kenway214
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.touch;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class DoubleTapService extends Service {
    private static final String TAG = "DoubleTapService";
    private static final String DT2W_CTL = "/vendor/bin/dt2w_ctl";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Starting DoubleTapService");
        registerObserver();
    }

    private void registerObserver() {
        ContentResolver cr = getContentResolver();
        cr.registerContentObserver(
            Settings.Secure.getUriFor(Settings.Secure.DOUBLE_TAP_TO_WAKE),
            true,
            new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange) {
                    updateMode();
                }
            }
        );
        updateMode();
    }

    private void updateMode() {
        int enabled = Settings.Secure.getInt(
            getContentResolver(),
            Settings.Secure.DOUBLE_TAP_TO_WAKE,
            0
        );
        Log.i(TAG, "DT2W setting changed: " + enabled);
        try {
            Process p = Runtime.getRuntime().exec(
                new String[]{DT2W_CTL, String.valueOf(enabled)}
            );
            p.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "Failed to execute dt2w_ctl", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
