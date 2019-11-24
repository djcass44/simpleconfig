/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package dev.dcas.simpleconfig.parse

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class EnvironmentAwareDeserializerTest {
    private val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    data class TestSubclass(
        @JsonDeserialize(using = EnvironmentAwareStringDeserializer::class)
        val value1: String,
        @JsonDeserialize(using = EnvironmentAwareStringDeserializer::class)
        val value2: String
    )

    data class TestClass(
        val value3: String,
        val value4: TestSubclass
    )

    @Test
    fun test() {
        System.setProperty("value4.value1", "foo")
        assertThat(System.getProperty("value4.value1"), `is`("foo"))

        val data = """
            value3: test3
            value4:
              value1: test1
              value2: test2
        """.trimIndent()

        val obj = mapper.readValue(data, TestClass::class.java)
        assertThat(obj.value4.value1, `is`("foo"))
    }
}