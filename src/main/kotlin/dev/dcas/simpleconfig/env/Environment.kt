/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.env

import dev.dcas.simpleconfig.util.StringUtil
import dev.dcas.util.extend.env

object Environment {
    fun get(name: String, default: String): String {
        // try to load from the environment first
        val res = StringUtil.mangleEnv(name).env("")
        return if(res.isNotBlank())
            res
        else // fallback to system properties otherwise
            System.getProperty(StringUtil.mangleProp(name), default)
    }
}