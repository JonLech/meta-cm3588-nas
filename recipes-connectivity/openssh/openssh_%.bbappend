FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://authorized_keys"

do_install:append() {
    install -d ${D}${ROOT_HOME}/.ssh
    install -m 0600 ${UNPACKDIR}/authorized_keys ${D}${ROOT_HOME}/.ssh/authorized_keys

    for file in sshd_config sshd_config_readonly; do
        sed -i 's/#PasswordAuthentication yes/PasswordAuthentication no/' ${D}${sysconfdir}/ssh/$file
    done

    # Need to wait for /etc/ssh overlay to be mounted before generating SSH keys
    sed -i '/^RequiresMountsFor=/s/$/ \/etc\/ssh/' ${D}${systemd_system_unitdir}/sshdgenkeys.service
}

FILES:${PN} += "${ROOT_HOME}/.ssh/authorized_keys"
