MACHINEOVERRIDES =. "rk3588:"
SOC_FAMILY = "rk3588"

# the rk3588s is a "stripped-down" version of the rk3588
# in the kernel's device-tree the rk3588 builds on top of the rk3588s
# so anything that is valid for the rk3588s is valid for the rk3588
require conf/machine/include/rk3588s.inc
