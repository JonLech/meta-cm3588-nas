require recipes-extended/images/core-image-full-cmdline.bb

IMAGE_FEATURES += "ssh-server-openssh"
IMAGE_FEATURES += "read-only-rootfs"

IMAGE_INSTALL:append = " vim xz zip tar"

# kernel modules
IMAGE_INSTALL:append = " kernel-modules"

# sound
IMAGE_INSTALL:append = " alsa-utils"

# FS utils
IMAGE_INSTALL:append = " \
    e2fsprogs e2fsprogs-resize2fs \
    dosfstools \
    parted \
    btrfs-tools \
"

# rauc
IMAGE_INSTALL:append = " rauc"

# PCI utils
IMAGE_INSTALL:append = " pciutils"

# NVME tool
IMAGE_INSTALL:append = " nvme-cli"

# NVME mount service
IMAGE_INSTALL:append = " nvme-mount"
SYSTEMD_AUTO_ENABLE:nvme-mount = "enable"

# avahi for zeroconf discovery
IMAGE_INSTALL:append = " avahi-daemon avahi-utils"

# samba for network storage
IMAGE_INSTALL:append = " samba"
SYSTEMD_AUTO_ENABLE:samba = "enable"

# cfg80211 regulatory.db
IMAGE_INSTALL:append = " wireless-regdb-static"

# firmware for Realtek NIC
IMAGE_INSTALL:append = " linux-firmware-rtl-nic"

# firmware for GPU
IMAGE_INSTALL:append = " linux-firmware-mali-csffw-arch108"

# Need to create these in advance due to read-only rootfs
ROOTFS_POSTPROCESS_COMMAND += "create_mount_points; "
create_mount_points() {
    mkdir -p ${IMAGE_ROOTFS}/data
    mkdir -p ${IMAGE_ROOTFS}/media/nvme
}

# We have an overlay for /etc/ssh so get rid of default file
do_delete_default_ssh() {
    rm -f ${IMAGE_ROOTFS}/etc/default/ssh
}
addtask delete_default_ssh after do_rootfs before do_image
