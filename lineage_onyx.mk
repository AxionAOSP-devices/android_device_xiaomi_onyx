#
# SPDX-FileCopyrightText: The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit_only.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common AxionOS stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Inherit from onyx device
$(call inherit-product, device/xiaomi/onyx/device.mk)

PRODUCT_NAME := lineage_onyx
PRODUCT_DEVICE := onyx
PRODUCT_MANUFACTURER := Xiaomi

# Set BUILD_FINGERPRINT variable to be picked up by both system and vendor build.prop
BuildFingerprint=POCO/onyx_global/onyx:16/BP2A.250605.031.A3/OS3.0.6.0.WOLMIXM:user/release-keys

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

# Axion Device Configuration
AXION_MAINTAINER := Zarathos_Ghost_Rider
AXION_PROCESSOR := SM8735

# Camera Info
AXION_CAMERA_REAR_INFO := 50,8
AXION_CAMERA_FRONT_INFO := 20

# Graphics & Display
TARGET_ENABLE_BLUR := true
TARGET_SUPPORTED_REFRESH_RATES := 60,90,120
HBM_SUPPORTED := true
HBM_NODE := /data/vendor/display/hbm_mode

# Features & Performance
TARGET_INCLUDE_AXFX := true
PERF_GOV_SUPPORTED := true
PERF_DEFAULT_GOV := walt
