do_install:append() {
    # match permissions in tmpfiles.d/polkit-tmpfiles.conf
    chmod 750 ${D}/${sysconfdir}/polkit-1/rules.d
    chown root:polkitd ${D}/${sysconfdir}/polkit-1/rules.d
}
