/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class StringUtilTest {
    @ParameterizedTest
    @CsvSource(value = [
        "simpleconfig.value1,SIMPLECONFIG_VALUE1",
        "server.ssl.enabled,SERVER_SSL_ENABLED",
        "server.ssl.key-store,SERVER_SSL_KEYSTORE",
        "server.ssl.key_store,SERVER_SSL_KEYSTORE"
    ])
    fun `environment variables are mangled correctly`(name: String, expected: String) {
        assertThat(StringUtil.mangleEnv(name), `is`(expected))
    }
}