FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://journald.conf"

do_install:append() {
     install -D -m0644 ${UNPACKDIR}/journald.conf ${D}${systemd_unitdir}/journald.conf.d/00-${PN}.conf
}
