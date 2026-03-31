#!/bin/bash

# Hardware xiaomi (fresh clone)
echo "Cloning hardware xiaomi source..."
rm -rf hardware/xiaomi
git clone -b lineage-23.2 https://github.com/sm8635-dev/hardware_xiaomi.git hardware/xiaomi

# Kernel source (fresh clone)
echo "Cloning kernel source tree..."
rm -rf device/xiaomi/onyx-kernel
git clone -b lineage-23.1 https://github.com/AxionAOSP-devices/android_kernel_xiaomi_onyx.git device/xiaomi/onyx-kernel

# MiuiCamera (fresh clone)
echo "Cloning MiuiCamera device tree..."
rm -rf device/xiaomi/onyx-miuicamera
git clone -b sixteen git@github.com:Zarathos30/device_xiaomi_peridot-miuicamera.git device/xiaomi/onyx-miuicamera

echo "Cloning MiuiCamera vendor tree..."
rm -rf vendor/xiaomi/onyx-miuicamera
git clone -b sixteen git@github.com:Zarathos30/vendor_xiaomi_peridot-miuicamera.git vendor/xiaomi/onyx-miuicamera

echo "vendorsetup.sh execution complete."
