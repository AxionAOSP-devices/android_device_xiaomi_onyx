#
# SPDX-FileCopyrightText: The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit_only.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common Lineage stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Inherit from onyx device
$(call inherit-product, device/xiaomi/onyx/device.mk)

PRODUCT_NAME := lineage_onyx
PRODUCT_DEVICE := onyx
PRODUCT_MANUFACTURER := Xiaomi
PRODUCT_BRAND := POCO
PRODUCT_MODEL := 25053PC47G

PRODUCT_SYSTEM_NAME := onyx_global
PRODUCT_SYSTEM_DEVICE := onyx

PRODUCT_BUILD_PROP_OVERRIDES += \
    BuildDesc="onyx_global-user 15 AQ3A.250226.002 OS3.0.302.0.WOLMIXM release-keys" \
    BuildFingerprint=POCO/onyx_global/onyx:15/AQ3A.250226.002/OS3.0.302.0.WOLMIXM:user/release-keys \
    DeviceName=$(PRODUCT_SYSTEM_DEVICE) \
    DeviceProduct=$(PRODUCT_SYSTEM_NAME)

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

# Axion Device Configuration
AXION_MAINTAINER := Zarathos_Ghost_Rider
AXION_PROCESSOR := SD8sG4

# Camera Info
AXION_CAMERA_REAR_INFO := 50,8
AXION_CAMERA_FRONT_INFO := 20

# Graphics & Display
TARGET_SUPPORTED_REFRESH_RATES := 60,90,120
HBM_SUPPORTED := true
HBM_NODE := /data/vendor/display/hbm_mode

# Features & Performance
BYPASS_CHARGE_SUPPORTED := true
BYPASS_CHARGE_TOGGLE_PATH ?= /sys/class/xm_power/charger/smart_charge/smart_night
TARGET_INCLUDE_AXFX := true
