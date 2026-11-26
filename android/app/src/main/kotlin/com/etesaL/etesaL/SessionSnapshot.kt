package com.etesaL.etesaL

import android.content.SharedPreferences

internal class SessionSnapshot(
    val backend: String,
    val tunnelMode: String,
    val config: String,
    val socksPort: Int,
    val exclude: Set<String>,
    val include: Set<String>,
    val username: String,
    val password: String,
    val serverName: String?,
) {
    fun writeTo(edit: SharedPreferences.Editor): SharedPreferences.Editor = edit
        .putString(EtesaLVpnService.KEY_QS_LAST_BACKEND, backend)
        .putString(EtesaLVpnService.KEY_QS_LAST_TUNNEL_MODE, tunnelMode)
        .putString(EtesaLVpnService.KEY_QS_LAST_XRAY_CONFIG, config)
        .putInt(EtesaLVpnService.KEY_QS_LAST_SOCKS_PORT, socksPort)
        .putString(EtesaLVpnService.KEY_QS_LAST_SOCKS_USERNAME, username)
        .putString(EtesaLVpnService.KEY_QS_LAST_SOCKS_PASSWORD, password)
        .putString(EtesaLVpnService.KEY_QS_LAST_SERVER_NAME, serverName)
        .putStringSet(EtesaLVpnService.KEY_QS_LAST_EXCLUDE_PACKAGES, exclude)
        .putStringSet(EtesaLVpnService.KEY_QS_LAST_INCLUDE_PACKAGES, include)

    companion object {
        // null, если файл конфига пропал (путь в prefs переживает очистку данных) или запись от старого AWG-ядра
        fun read(prefs: SharedPreferences, configExists: (String) -> Boolean): SessionSnapshot? {
            val backend = prefs.getString(EtesaLVpnService.KEY_QS_LAST_BACKEND, EtesaLVpnService.VPN_BACKEND_XRAY)
                ?: EtesaLVpnService.VPN_BACKEND_XRAY
            if (backend != EtesaLVpnService.VPN_BACKEND_XRAY && backend != EtesaLVpnService.VPN_BACKEND_MIHOMO) {
                return null
            }
            val config = prefs.getString(EtesaLVpnService.KEY_QS_LAST_XRAY_CONFIG, null)
            val user = prefs.getString(EtesaLVpnService.KEY_QS_LAST_SOCKS_USERNAME, null)
            val pass = prefs.getString(EtesaLVpnService.KEY_QS_LAST_SOCKS_PASSWORD, null)
            if (config.isNullOrBlank() || user.isNullOrBlank() || pass.isNullOrBlank() || !configExists(config)) {
                return null
            }
            return SessionSnapshot(
                backend = backend,
                tunnelMode = prefs.getString(EtesaLVpnService.KEY_QS_LAST_TUNNEL_MODE, EtesaLVpnService.TUNNEL_MODE_VPN)
                    ?: EtesaLVpnService.TUNNEL_MODE_VPN,
                config = config,
                socksPort = prefs.getInt(EtesaLVpnService.KEY_QS_LAST_SOCKS_PORT, 2080),
                exclude = prefs.getStringSet(EtesaLVpnService.KEY_QS_LAST_EXCLUDE_PACKAGES, emptySet()) ?: emptySet(),
                include = prefs.getStringSet(EtesaLVpnService.KEY_QS_LAST_INCLUDE_PACKAGES, emptySet()) ?: emptySet(),
                username = user,
                password = pass,
                serverName = prefs.getString(EtesaLVpnService.KEY_QS_LAST_SERVER_NAME, null)
                    ?.takeIf { it.isNotBlank() },
            )
        }
    }
}
