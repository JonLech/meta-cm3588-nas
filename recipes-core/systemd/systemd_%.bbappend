FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-add-noexec-to-user-runtime-tmp-dir.patch"

inherit overlayfs

OVERLAYS="/var/lib /etc/ssh"
OVERLAYFS_WRITABLE_PATHS[data] = "${OVERLAYS}"
SYSTEMD_SERVICE:${BPN}:remove = "${BPN}-overlays.service"

do_install:append() {
    rm -f ${D}${systemd_system_unitdir}/${BPN}-overlays.service

    for overlay in ${OVERLAYS}; do
        unit_name=$(echo "$overlay" | sed 's|/||;s|/|-|g')
        sed -i 's/WantedBy=.*/WantedBy=local-fs.target/' ${D}${systemd_system_unitdir}/${unit_name}.mount
    done

    for service in systemd-timesyncd.service systemd-resolved.service; do
        sed -i '/^After=/i RequiresMountsFor=/var/lib' ${D}${systemd_system_unitdir}/${service}
    done

    sed -i 's/nosuid/noexec,nosuid/' ${D}${systemd_system_unitdir}/tmp.mount
}
