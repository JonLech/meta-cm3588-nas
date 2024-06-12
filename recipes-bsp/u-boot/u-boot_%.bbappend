require u-boot-rockchip.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:nanopc-nas = " \
       git://github.com/Kwiboo/u-boot-rockchip.git;protocol=https;branch=rk3xxx-2024.07 \
       file://0001-Add-nanopc-nas-rk3588-board.patch \
       "
SRCREV:nanopc-nas = "${AUTOREV}"
SRCREV:nanopc-nas:rk-u-boot-env = "${AUTOREV}"

SRC_URI:append:rk-u-boot-env = " file://rockchip-enable-environment-mmc.cfg"
SRCREV:rk-u-boot-env = "cdfcc37428e06f4730ab9a17cc084eeb7676ea1a"

DEPENDS:append:rk3308 = " u-boot-tools-native"
DEPENDS:append:rock-pi-4 = " gnutls-native"
DEPENDS:append:rk-u-boot-env = " u-boot-mkenvimage-native"

do_compile:append:rock2-square () {
	# copy to default search path
	if [ "${SPL_BINARY}" = "u-boot-spl-dtb.bin" ]; then
		cp ${B}/spl/${SPL_BINARY} ${B}
	fi
}

python rk_no_env() {
    if bb.utils.contains('MACHINE_FEATURES', 'rk-u-boot-env', True, False, d):
        bb.warn("the rk-u-boot-env MACHINE_FEATURE is not supported for this build")
}

rk_generate_env() {
	if [ ! -f "${B}/.config" ]; then
		echo "U-Boot .config not found, can't determine environment size"
		return 1
	fi
	cat ${B}/.config | grep "^CONFIG_ENV_SIZE=" > /dev/null
	if [ $? -ne 0 ]; then
		echo "can not find CONFIG_ENV_SIZE value in U-Boot .config"
		return 1
	fi

	UBOOT_ENV_SIZE="$(cat ${B}/.config | grep "^CONFIG_ENV_SIZE=" | cut -d'=' -f2)"

	# linux user-space U-Boot env config file
	echo "/dev/disk/by-partlabel/uboot_env 0x0000 ${UBOOT_ENV_SIZE}" > ${WORKDIR}/fw_env.config

	# convert text-based environment to binary suitable for image
	if [ "${@bb.utils.to_boolean(d.getVar('RK_IMAGE_INCLUDES_UBOOT_ENV'), False)}" = "True" ]; then
		if [ ! -f ${B}/u-boot-initial-env ]; then
			echo "initial, text-formatted U-Boot environment file \"${B}/u-boot-initial-env\" not found"
			return 1
		fi
		mkenvimage -s ${UBOOT_ENV_SIZE} ${B}/u-boot-initial-env -o ${WORKDIR}/u-boot.env
	fi
}
do_compile[postfuncs] += "${@'rk_generate_env' if 'rk-u-boot-env' in d.getVar('MACHINEOVERRIDES').split(':') else 'rk_no_env'}"

do_deploy:append:rk-u-boot-env() {
	if [ -f ${WORKDIR}/u-boot.env -a "${@bb.utils.to_boolean(d.getVar('RK_IMAGE_INCLUDES_UBOOT_ENV'),False)}" = "True" ]; then
		install -d ${DEPLOYDIR}
		install -m 0644 ${WORKDIR}/u-boot.env ${DEPLOYDIR}
	fi
}
