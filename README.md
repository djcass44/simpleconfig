# SimpleConfig

This library provides a barebones method for pulling application configuration from a YAML file.

### Features

* Load from classpath
* Load from filesystem
* Load from URL
* Environment/system override*

Environment can only override values which are present.

### Usage

Import library (`build.gradle.kts`)
```kotlin
implementation("com.github.djcass44:simpleconfig:0.1")
```

Define a schema

```kotlin
data class MyConfig(
    val value1: String,
    val value2: String,
    val value3: Int,
    val value4: Boolean
)
```

Load the configuration

```kotlin
val config = ConfigLoader(MyConfig::class.java).load("classpath:development.yaml")
```

#### Configuration sources

Files are loaded in the following priority:

1. User specified
2. Classpath `application.yaml/yml`

**Loading from filesystem**

This is the default method for locating a file.

```kotlin
val config = ConfigLoader(MyConfig::class.java).load("/opt/myapp/config/production.yml")
```

**Loading from URL**

Any schema supported by `URL::openStream` should work, however only `http://` and `https://` have been tested.

Example:

```kotlin
val config = ConfigLoader(MyConfig::class.java).load("https://example.org/config/dev.yaml")
```

**Loading from classpath**

Prefixing `classpath:` will indicate that the file should be loaded from the classpath.

Example:

```kotlin
val config = ConfigLoader(MyConfig::class.java).load("classpath:development.yaml")
```

This project is licensed under the Mozilla Public License Version 2.0.
Please see [LICENSE](LICENSE) for more information.