SOC_FAMILY = "rk3308"

DEFAULTTUNE ?= "cortexa35-crypto"

ROCKCHIP_CLOSED_TPL ?= "1"

require conf/machine/include/soc-family.inc
require conf/machine/include/rockchip-defaults.inc
require conf/machine/include/arm/armv8a/tune-cortexa35.inc
require conf/machine/include/rockchip-wic.inc

SERIAL_CONSOLES = "1500000;ttyS0"

KBUILD_DEFCONFIG ?= "defconfig"
KERNEL_FEATURES:append:rk3308 = " bsp/rockchip/remove-non-rockchip-arch-arm64.scc"
KERNEL_CLASSES = "kernel-fitimage"
KERNEL_IMAGETYPE ?= "fitImage"

UBOOT_SUFFIX ?= "itb"
UBOOT_ENTRYPOINT ?= "0x06000000"

PREFERRED_PROVIDER_rockchip-rkbin = "${@bb.utils.contains('RKBIN_RK3308_LATEST', '1', 'rockchip-rkbin', 'rk3308-rkbin', d)}"
