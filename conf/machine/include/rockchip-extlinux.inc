# KERNEL_DEVICETREE follows the pattern of
# 'rockchip/${SOC_FAMILY}-${BOARD}.dtb' which is required when compiling
# the kernel so the kernel's Makefile can find the device tree
#
# when using extlinux with a fitImage the device tree is contained in the
# fitImage
#
# however for non-fitImages it is not found at
# $UBOOT_EXTLINUX_FDTDIR/rockchip/$KERNEL_DEVICETREE but rather at
# $UBOOT_EXTLINUX_FDTDIR/$KERNEL_DEVICETREE therefore we need to strip off
# the 'rockchip/' part for extlinux non-fitImage builds
NONFITDT ?= "${@d.getVar('KERNEL_DEVICETREE').split()[0].split('/')[1]}"

UBOOT_EXTLINUX ?= "1"
UBOOT_EXTLINUX_ROOT ?= "root=PARTLABEL=rootfsA"
UBOOT_EXTLINUX_FDTDIR ?= "${@bb.utils.contains('KERNEL_IMAGETYPE', 'fitImage', '', '/boot', d)}"
UBOOT_EXTLINUX_FDT ?= "${@bb.utils.contains('KERNEL_IMAGETYPE', 'fitImage', '', '/boot/${NONFITDT}', d)}"
UBOOT_EXTLINUX_CONSOLE ?= "earlycon console=tty1 console=${RK_CONSOLE_DEVICE},${RK_CONSOLE_BAUD}n8"
UBOOT_EXTLINUX_KERNEL_ARGS ?= "rootwait rw rootfstype=ext4"
UBOOT_EXTLINUX_KERNEL_IMAGE ?= "/boot/${KERNEL_IMAGETYPE}"
UBOOT_EXTLINUX_LABELS ?= "default"
UBOOT_EXTLINUX_MENU_DESCRIPTION:default ?= "${MACHINE}"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "u-boot-extlinux"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-image ${@bb.utils.contains('KERNEL_IMAGETYPE', 'fitImage', '', 'kernel-devicetree', d)}"
