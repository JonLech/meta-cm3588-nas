# Copyright (C) 2015 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

SOC_FAMILY = "rk3288"

require conf/machine/include/soc-family.inc
require conf/machine/include/rockchip-defaults.inc
require conf/machine/include/arm/armv7a/tune-cortexa17.inc

SERIAL_CONSOLES = "115200;ttyS2"

KBUILD_DEFCONFIG ?= "multi_v7_defconfig"
KERNEL_FEATURES:append:rk3288 = " bsp/rockchip/remove-non-rockchip-arch-arm.scc"
KERNEL_IMAGETYPE ?= "zImage"

UBOOT_SUFFIX ?= "bin"
