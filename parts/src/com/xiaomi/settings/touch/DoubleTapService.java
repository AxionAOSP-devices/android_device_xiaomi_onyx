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
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;

public class DoubleTapService extends Service {
    private static final String TAG = "DoubleTapService";
    private static final String DT2W_PROP = "persist.vendor.xiaomi.dt2w";

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
        SystemProperties.set(DT2W_PROP, String.valueOf(enabled));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
