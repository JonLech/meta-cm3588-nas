FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

COMPATIBLE_MACHINE:nanopc-nas = "nanopc-nas"

SRC_URI:append:nanopc-nas = " \
    file://rockchip-kmeta;type=kmeta;name=rockchip-kmeta;destsuffix=rockchip-kmeta \
    file://0001-Add-ethernet-node-and-alias-to-friendlyelec-cm3588-D.patch \
    file://squashfs.cfg \
    file://rt5616.cfg \
"
