# common meta-rockchip wic/wks items

require conf/machine/include/rockchip-extlinux.inc
require conf/machine/include/rockchip-rk-u-boot-env.inc

SPL_BINARY ?= "idbloader.img"

# if you use the following variable, make sure to add the '.' e.g.
# WIC_COMPRESSION_EXTENSION = ".xz"
WIC_COMPRESSION_EXTENSION ?= ""
IMAGE_FSTYPES += "wic${WIC_COMPRESSION_EXTENSION} wic.bmap"
WKS_FILE ?= "rockchip.wks"
WKS_FILE_DEPENDS ?= " \
	e2fsprogs-native \
	virtual/bootloader \
	"

RK_IMAGE_INCLUDES_UBOOT_ENV ?= "no"
RK_UBOOT_ENV = " "
RK_UBOOT_ENV:rk-u-boot-env = "${@ '--source rawcopy --sourceparams=file=u-boot.env' \
	if bb.utils.to_boolean(d.getVar('RK_IMAGE_INCLUDES_UBOOT_ENV'), False) else ' '}"

WICVARS:append = " \
	RK_UBOOT_ENV \
	ROOT_DPS \
	SPL_BINARY \
	UBOOT_SUFFIX \
	"
