/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.parse

import com.fasterxml.jackson.core.JsonStreamContext
import com.fasterxml.jackson.databind.JsonDeserializer

abstract class EnvironmentAwareDeserializer<T>: JsonDeserializer<T>() {
    /**
     * Traverse back to the top of the tree to find the path
     * E.g. server.ssl.enabled
     */
    fun getPath(ctx: JsonStreamContext, path: String = ""): String {
        if(ctx.inRoot())
            return path
        return if(path.isEmpty())
            getPath(ctx.parent, ctx.currentName)
        else
            getPath(ctx.parent, "${ctx.currentName}.$path")
    }
}