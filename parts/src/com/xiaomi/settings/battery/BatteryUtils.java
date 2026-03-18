/*
 * Copyright (C) 2023-2024 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings.battery;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BatteryUtils {
    private static final String TAG = "BatteryUtils";
    public static final String PREF_CHARGING_CTRL = "charging_control";
    public static final String PREF_CHARGING_LIMIT = "charging_limit";
    private static final String INPUT_SUSPEND_PATH =
            "/sys/devices/platform/soc/soc:mca_charge_interface/input_suspend";

    public static void setChargingSuspend(boolean suspend) {
        File file = new File(INPUT_SUSPEND_PATH);
        if (!file.exists()) return;
        String value = "micharger buck " + (suspend ? "1" : "0");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(value.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            try {
                fos.getFD().sync();
            } catch (IOException ignored) {}
            Log.d(TAG, "Successfully wrote " + value + " to " + INPUT_SUSPEND_PATH);
        } catch (IOException e) {
            Log.e(TAG, "Failed to write to " + INPUT_SUSPEND_PATH, e);
        }
    }

    public static void setChargingSuspendAsync(boolean suspend) {
        new Thread(() -> setChargingSuspend(suspend)).start();
    }
}
