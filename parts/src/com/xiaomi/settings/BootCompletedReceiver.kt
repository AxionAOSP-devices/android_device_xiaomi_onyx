/*
 * Copyright (C) 2023-2024 Paranoid Android
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.xiaomi.settings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.UserHandle
import android.util.Log
import android.view.Display
import android.view.Display.HdrCapabilities

import com.xiaomi.settings.battery.ChargingLimitService
import com.xiaomi.settings.display.ColorModeService
import com.xiaomi.settings.touch.DoubleTapService
import com.xiaomi.settings.touch.TouchReportRateService

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (DEBUG) Log.i(TAG, "Received intent: ${intent.action}")
        when (intent.action) {
            Intent.ACTION_LOCKED_BOOT_COMPLETED -> onLockedBootCompleted(context)
            Intent.ACTION_BOOT_COMPLETED -> onBootCompleted(context)
        }
    }

    companion object {
        private const val TAG = "XiaomiParts"
        private const val DEBUG = true

        private fun onLockedBootCompleted(context: Context) {
            // Display
            context.startServiceAsUser(
                Intent(context, ColorModeService::class.java),
                UserHandle.CURRENT
            )

            // Double Tap to Wake
            context.startServiceAsUser(
                Intent(context, DoubleTapService::class.java),
                UserHandle.CURRENT
            )

            // Battery
            context.startServiceAsUser(
                Intent(context, ChargingLimitService::class.java),
                UserHandle.CURRENT
            )

            // Touch
            TouchReportRateService.startService(context)

            // Override HDR types to enable Dolby Vision
            val displayManager = context.getSystemService(DisplayManager::class.java)
            displayManager.overrideHdrTypes(
                Display.DEFAULT_DISPLAY,
                intArrayOf(
                    HdrCapabilities.HDR_TYPE_DOLBY_VISION,
                    HdrCapabilities.HDR_TYPE_HDR10,
                    HdrCapabilities.HDR_TYPE_HLG,
                    HdrCapabilities.HDR_TYPE_HDR10_PLUS
                )
            )
        }

        private fun onBootCompleted(context: Context) {
        }
    }
}
