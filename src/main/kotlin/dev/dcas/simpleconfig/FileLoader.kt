/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig

import dev.castive.log2.loge
import java.io.BufferedInputStream
import java.io.File
import java.net.URL

class FileLoader {
    /**
     * Load data from the classpath
     */
    fun fromClasspath(name: String): String? = javaClass.classLoader.getResourceAsStream(name)?.bufferedReader()?.readText()

    /**
     * Load data from the filesystem
     * @return null if the file doesn't exist or can't be read
     */
    fun fromFilesystem(name: String): String? {
        val file = File(name)
        if(!file.exists()) {
            "Requested file doesn't exist: $name".loge(javaClass)
            return null
        }
        if(!file.canRead()) {
            "No read permissions for file: $name".loge(javaClass)
            return null
        }
        return try {
            file.readText()
        }
        catch (e: Exception) {
            "Failed to read file: $name, $e".loge(javaClass)
            null
        }
    }

    /**
     * Load data from a url
     */
    fun fromUrl(url: String): String? {
        // try to parse the url first
        val fileUrl = kotlin.runCatching { URL(url) }.getOrNull() ?: run {
            "Unable to parse URL: $url".loge(javaClass)
            return null
        }
        return try {
            BufferedInputStream(fileUrl.openStream()).bufferedReader().readText()
        }
        catch (e: Exception) {
            "Failed to read url: $url, $e".loge(javaClass)
            null
        }
    }
}