DESCRIPTION = "Rockchip Firmware and Tool Binaries"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM:rk3308 = "file://README;md5=39cc9df955478b8df26158d489fdcc95"

SRC_URI = "git://github.com/rockchip-linux/rkbin;protocol=https;branch=master"
SRCREV = "e65b97b511f1349156702db40694454c141d8fe2"

PROVIDES += "trusted-firmware-a"
PROVIDES += "rockchip-rkbin"
PROVIDES += "optee-os"

inherit bin_package deploy

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:rk3308 = "rk3308"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {
	# Nothing in this recipe is useful in a filesystem
	:
}

PACKAGES = "${PN}"
ALLOW_EMPTY:${PN} = "1"

do_deploy:rk3308() {
	# Prebuilt TF-A
	install -m 644 ${S}/bin/rk33/rk3308_bl31_v*.elf ${DEPLOYDIR}/bl31-rk3308.elf
	# Prebuilt OPTEE-OS
	install -m 644 ${S}/bin/rk33/rk3308_bl32_v*.bin ${DEPLOYDIR}/tee-rk3308.bin
	# Prebuilt U-Boot TPL (DDR init)
	install -m 644 ${S}/bin/rk33/rk3308_ddr_589MHz_uart0_m0_v*.bin ${DEPLOYDIR}/ddr-rk3308.bin
}

do_deploy() {
	bbfatal "COMPATIBLE_MACHINE requires a corresponding do_deploy:<MACHINE>() override"
}

addtask deploy after do_install
