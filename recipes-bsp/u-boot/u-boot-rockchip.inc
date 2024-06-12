BL31:rockchip:aarch64 = "${DEPLOY_DIR_IMAGE}/bl31-${SOC_FAMILY}.elf"
# SOC_FAMILY for RK3588S is rk3588s but it should use the binaries from rk3588
BL31:rk3588s = "${DEPLOY_DIR_IMAGE}/bl31-rk3588.elf"
EXTRA_OEMAKE:append:rockchip:aarch64 = " BL31=${BL31}"

TFA_DEPENDS ??= ""
TFA_DEPENDS:rockchip:aarch64 = " trusted-firmware-a:do_deploy"
do_compile[depends] .= "${TFA_DEPENDS}"

# No open-source TPL (yet)
ROCKCHIP_TPL:closed-tpl = "${DEPLOY_DIR_IMAGE}/ddr-${SOC_FAMILY}.bin"
# SOC_FAMILY for RK3588S is rk3588s but it should use the binaries from rk3588
ROCKCHIP_TPL:closed-tpl:rk3588s = "${DEPLOY_DIR_IMAGE}/ddr-rk3588.bin"
EXTRA_OEMAKE:append:closed-tpl = " ROCKCHIP_TPL=${ROCKCHIP_TPL}"

INIT_FIRMWARE_DEPENDS ??= ""
INIT_FIRMWARE_DEPENDS:closed-tpl = " rockchip-rkbin:do_deploy"
do_compile[depends] .= "${INIT_FIRMWARE_DEPENDS}"
