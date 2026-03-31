#!/bin/bash

# Hardware xiaomi (fresh clone)
echo "Cloning hardware xiaomi source..."
rm -rf hardware/xiaomi
git clone -b lineage-23.2 https://github.com/sm8635-dev/hardware_xiaomi.git hardware/xiaomi

# Kernel source (fresh clone)
echo "Cloning kernel source tree..."
rm -rf device/xiaomi/onyx-kernel
git clone -b lineage-23.1 https://github.com/AxionAOSP-devices/android_kernel_xiaomi_onyx.git device/xiaomi/onyx-kernel

echo "vendorsetup.sh execution complete."
