/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.parse

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import dev.castive.log2.logd
import dev.dcas.simpleconfig.env.Environment

class EnvironmentAwareStringDeserializer: EnvironmentAwareDeserializer<String>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): String {
        val path = getPath(p.parsingContext)
        val value = p.text
        // get the override from the environment with the actual value as a fallback
        val envValue = Environment.get(path, value)
        if(envValue != value)
            "Got overridden value: $path".logd(javaClass)
        return envValue
    }
}