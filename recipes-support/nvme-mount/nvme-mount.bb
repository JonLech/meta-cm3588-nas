SUMMARY = "NVME mount service"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "\
    file://${BPN}.service;subdir=sources \
    file://${BPN};subdir=sources \
"
S = "${WORKDIR}/sources"

inherit allarch

do_configure[noexec] = "1"
do_compile[noexec] = "1"

RDEPENDS:${PN} = " \
    udisks2 \
    jq \
"

inherit systemd

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/${BPN}.service ${D}${systemd_system_unitdir}/

    install -d ${D}${libexecdir}
    install -m 0755 ${S}/${BPN} ${D}${libexecdir}/
}

FILES:${PN} += "\
  ${libexecdir}/${BPN} \
"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"
