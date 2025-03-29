FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://nvme.conf \
    file://includes.conf \
    file://pam.conf \
"

do_install:append() {
    sed -i '/^After=/ s/$/ nvme-mount.service/' ${D}${systemd_unitdir}/system/smb.service
    sed -i '$a[global]\ninclude = /etc/${BPN}/includes.conf' ${D}${sysconfdir}/${BPN}/smb.conf
    install -d ${D}${sysconfdir}/${BPN}/smb.conf.d
    install -m 0644 ${UNPACKDIR}/nvme.conf ${D}${sysconfdir}/${BPN}/smb.conf.d/
    install -m 0644 ${UNPACKDIR}/includes.conf ${D}${sysconfdir}/${BPN}/
    install -d ${D}${sysconfdir}/pam.d
    install -m 0644 ${UNPACKDIR}/pam.conf ${D}${sysconfdir}/pam.d/${BPN}
}
