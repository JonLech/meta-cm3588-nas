PACKAGECONFIG:append = " btrfs"

do_install:append() {
    rm -f ${D}${libdir}/udisks2/modules/*.a
}
