# meta-cm3588-nas

Yocto BSP layer for the [FriendlyElec CM3588 NAS](https://www.friendlyelec.com/index.php?route=product/product&product_id=294).

This layer uses a mainline Linux kernel (v6.14).

## Build Setup

To fetch layer dependencies and build the image easily, install [kas](https://github.com/siemens/kas):

```shell
$ pip install kas
```

## SSH Key

The build includes an SSH daemon (sshd). Add your public key to *recipes-connectivity/openssh/files/authorized_keys*.

## Root Password

Change the default root password at the bottom of *cm3588-nas.yml*.

## Building

```shell
$ KAS_CLONE_DEPTH=1 kas build cm3588-nas.yml
```

The build output directory is *build/tmp/deploy/images/nanopc-nas*.

## Flashing the Device (eMMC)

To flash the device, install [rkdeveloptool](https://github.com/rockchip-linux/rkdeveloptool).

With the power off and the USB-C port on the CM3588 NAS connected to your Mac or PC, hold the [mask button](https://www.friendlyelec.com/image/catalog/description/CM3588_en_05.jpg) and connect power to the device.

Verify that the device appears in Maskrom mode:

```shell
$ rkdeveloptool ld
DevNo=1	Vid=0x2207,Pid=0x350b,LocationID=802	Maskrom
```

Build the RK3588 loader:

```shell
$ git clone https://github.com/rockchip-linux/rkbin --depth 1
$ (cd rkbin; ./tools/boot_merger RKBOOT/RK3588MINIALL.ini)
```

Push the loader to the device:

```shell
$ rkdeveloptool db rkbin/rk3588_spl_loader_v1.18.113.bin
$ rkdeveloptool ul rkbin/rk3588_spl_loader_v1.18.113.bin
```

Flash the image to the device:

```shell
$ rkdeveloptool wl 0 cm3588nas-image-nanopc-nas.rootfs.wic
```

Reboot the device:

```shell
$ rkdeveloptool rd
```

## NVMe Drive (When Present)

If */dev/nvme0n1* has a partition table, no action will be taken on boot.

If */dev/nvme0n1* has neither a filesystem nor a partition table, it will be formatted with [btrfs](https://btrfs.readthedocs.io).

If */dev/nvme0n1* has a filesystem, it will be mounted at */media/nvme*.


See the [nvme-mount](recipes-support/nvme-mount/files/nvme-mount) script for the exact logic.

## Additional NVMe Drives

With additional drives, you can create various RAID setups using btrfs. To set up RAID 10, run the following commands after connecting via SSH:

```shell
# MOUNT_POINT=$(busctl get-property --json=pretty org.freedesktop.UDisks2 /org/freedesktop/UDisks2/block_devices/nvme0n1 org.freedesktop.UDisks2.Filesystem MountPoints | jq -r '.data[-1] | join(" ")' | xargs -n1 | grep -v '^0$' | while read -r num; do printf "\x$(printf %02x $num)"; done)
# btrfs device add /dev/nvme0n2 /dev/nvme0n3 /dev/nvme0n4 "$MOUNT_POINT"
# btrfs balance start -dconvert=raid10 -mconvert=raid10 "$MOUNT_POINT"
```

## Samba NVMe Share

The NVMe drive will be [shared](recipes-connectivity/samba/files/nvme.conf) on your network at *smb://nanopc-nas/nvme*.

Username: *nas*

To access the share, first set a password by connecting over SSH and running *smbpasswd*:

```shell
# smbpasswd -a nas
```
