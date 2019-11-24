/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.util

object StringUtil {
    /**
     * Convert a property value into one that can be read as an Environment variable
     *
     * - _(underscore) is removed
     *
     * - -(hyphen) is removed
     *
     * - .(full-stop) is replaced with _ (underscore)
     */
    fun mangleEnv(name: String): String {
        return name.toUpperCase()
            .replace("_", "")
            .replace("-", "")
            .replace(".", "_")
    }
    fun mangleProp(name: String): String {
        return name
    }
}