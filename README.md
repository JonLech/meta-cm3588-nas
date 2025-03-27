# meta-cm3588-nas

Yocto BSP layer for [FriendlyElec CM3588 NAS](https://www.friendlyelec.com/index.php?route=product/product&product_id=294).

This layer uses a mainline Linux kernel (v6.14).

## Build setup

To easily fetch layer dependencies and build the image, you'll need to install [kas](https://github.com/siemens/kas):

```shell
~ $ pip install kas
```

## ssh key

The build will include sshd. Add your public key to *recipes-connectivity/openssh/files/authorized_keys*.

## root password

Change the default root password at the bottom of *cm3588-nas.yml*.

## Building

```
~ $ KAS_CLONE_DEPTH=1 kas build cm3588-nas.yml
```

Build output directory: build/tmp/deploy/images/nanopc-nas

## Flashing device (eMMC)

To flash the device you will need rkdeveloptool: https://github.com/rockchip-linux/rkdeveloptool

With power off and the USB-C on the CM3588 NAS connected to your Mac/PC, hold the [mask button](https://www.friendlyelec.com/image/catalog/description/CM3588_en_05.jpg) and connect power to the device.

Verify that the device appears in maskrom mode:

```shell
$ rkdeveloptool ld
DevNo=1	Vid=0x2207,Pid=0x350b,LocationID=802	Maskrom
```

Build the RK3588 loader:

```shell
git clone https://github.com/rockchip-linux/rkbin --depth 1
(cd rkbin; ./tools/boot_merger RKBOOT/RK3588MINIALL.ini)
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

Reboot device:

```shell
$ rkdeveloptool rd
```
