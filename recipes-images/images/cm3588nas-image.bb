require recipes-extended/images/core-image-full-cmdline.bb

IMAGE_FEATURES += "ssh-server-openssh"
IMAGE_FEATURES += "read-only-rootfs"

IMAGE_INSTALL:append = " vim xz zip tar"

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

# kernel modules
IMAGE_INSTALL:append = " kernel-module-crc32c-generic"
IMAGE_INSTALL:append = " kernel-module-cfg80211"
IMAGE_INSTALL:append = " kernel-module-rfkill"
IMAGE_INSTALL:append = " kernel-module-btrfs"
IMAGE_INSTALL:append = " kernel-module-blake2b-generic"
IMAGE_INSTALL:append = " kernel-module-xor"
IMAGE_INSTALL:append = " kernel-module-xor-neon"
IMAGE_INSTALL:append = " kernel-module-raid6-pq"
IMAGE_INSTALL:append = " kernel-module-zstd-compress"
IMAGE_INSTALL:append = " kernel-module-gpio-ir-recv"
IMAGE_INSTALL:append = " kernel-module-pwm-fan"
IMAGE_INSTALL:append = " kernel-module-rc-core"
IMAGE_INSTALL:append = " kernel-module-pwm-beeper"
IMAGE_INSTALL:append = " kernel-module-hantro-vpu"
IMAGE_INSTALL:append = " kernel-module-v4l2-vp9"
IMAGE_INSTALL:append = " kernel-module-snd-soc-rt5616"
IMAGE_INSTALL:append = " kernel-module-videobuf2-dma-contig"
IMAGE_INSTALL:append = " kernel-module-videobuf2-memops"
IMAGE_INSTALL:append = " kernel-module-fusb302"
IMAGE_INSTALL:append = " kernel-module-snd-soc-rl6231"
IMAGE_INSTALL:append = " kernel-module-v4l2-jpeg"
IMAGE_INSTALL:append = " kernel-module-tcpm"
IMAGE_INSTALL:append = " kernel-module-phy-rockchip-usbdp"
IMAGE_INSTALL:append = " kernel-module-v4l2-h264"
IMAGE_INSTALL:append = " kernel-module-typec"
IMAGE_INSTALL:append = " kernel-module-rtc-hym8563"
IMAGE_INSTALL:append = " kernel-module-rockchip-saradc"
IMAGE_INSTALL:append = " kernel-module-phy-rockchip-samsung-hdptx"
IMAGE_INSTALL:append = " kernel-module-rockchipdrm"
IMAGE_INSTALL:append = " kernel-module-rk805-pwrkey"
IMAGE_INSTALL:append = " kernel-module-phy-rockchip-naneng-combphy"
IMAGE_INSTALL:append = " kernel-module-industrialio-triggered-buffer"
IMAGE_INSTALL:append = " kernel-module-v4l2-mem2mem"
IMAGE_INSTALL:append = " kernel-module-kfifo-buf"
IMAGE_INSTALL:append = " kernel-module-rockchip-thermal"
IMAGE_INSTALL:append = " kernel-module-dw-hdmi-qp"
IMAGE_INSTALL:append = " kernel-module-analogix-dp"
IMAGE_INSTALL:append = " kernel-module-videobuf2-v4l2"
IMAGE_INSTALL:append = " kernel-module-nvme"
IMAGE_INSTALL:append = " kernel-module-snd-soc-rockchip-i2s-tdm"
IMAGE_INSTALL:append = " kernel-module-videobuf2-common"
IMAGE_INSTALL:append = " kernel-module-dw-mipi-dsi"
IMAGE_INSTALL:append = " kernel-module-videodev"
IMAGE_INSTALL:append = " kernel-module-dw-hdmi"
IMAGE_INSTALL:append = " kernel-module-mc"
IMAGE_INSTALL:append = " kernel-module-panthor"
IMAGE_INSTALL:append = " kernel-module-nvme-core"
IMAGE_INSTALL:append = " kernel-module-drm-display-helper"
IMAGE_INSTALL:append = " kernel-module-drm-shmem-helper"
IMAGE_INSTALL:append = " kernel-module-cec"
IMAGE_INSTALL:append = " kernel-module-gpu-sched"
IMAGE_INSTALL:append = " kernel-module-drm-client-lib"
IMAGE_INSTALL:append = " kernel-module-pci-endpoint-test"
IMAGE_INSTALL:append = " kernel-module-drm-dma-helper"
IMAGE_INSTALL:append = " kernel-module-drm-gpuvm"
IMAGE_INSTALL:append = " kernel-module-drm-exec"
IMAGE_INSTALL:append = " kernel-module-drm-kms-helper"
IMAGE_INSTALL:append = " kernel-module-adc-keys"
IMAGE_INSTALL:append = " kernel-module-snd-soc-simple-card"
IMAGE_INSTALL:append = " kernel-module-snd-soc-simple-card-utils"
IMAGE_INSTALL:append = " kernel-module-r8169"
IMAGE_INSTALL:append = " kernel-module-fuse"
IMAGE_INSTALL:append = " kernel-module-drm"
IMAGE_INSTALL:append = " kernel-module-backlight"
IMAGE_INSTALL:append = " kernel-module-nfnetlink"
IMAGE_INSTALL:append = " kernel-module-ipv6"
