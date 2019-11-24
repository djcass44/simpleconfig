/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dev.dcas.simpleconfig.parse.EnvironmentAwareBooleanDeserializer
import dev.dcas.simpleconfig.parse.EnvironmentAwareDeserializer
import dev.dcas.simpleconfig.parse.EnvironmentAwareIntDeserializer
import dev.dcas.simpleconfig.parse.EnvironmentAwareStringDeserializer

class ConfigLoader<T>(private val configSchema: Class<out T>) {
    companion object {
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

        init {
            // add our custom module for overriding properties
            val module = SimpleModule(
                EnvironmentAwareDeserializer::class.java.simpleName,
                Version.unknownVersion()).apply {
                addDeserializer(String::class.java, EnvironmentAwareStringDeserializer())
                addDeserializer(Int::class.java, EnvironmentAwareIntDeserializer())
                addDeserializer(Boolean::class.java, EnvironmentAwareBooleanDeserializer())
            }
            mapper.registerModule(module)
        }
    }

    private val loader = FileLoader()

    fun load(uri: String): T {
        val data = when {
            uri.startsWith("http") -> loader.fromUrl(uri)
            uri.startsWith("/") || uri.startsWith("~") -> loader.fromFilesystem(uri)
            uri.startsWith("classpath:") -> loader.fromClasspath(uri.split(":")[1])
            else -> null
        } ?: loader.fromClasspath("application.yaml") ?: loader.fromClasspath("application.yml") ?: loader.fromClasspath("application.json")
        return mapper.readValue(data, configSchema)
    }
}