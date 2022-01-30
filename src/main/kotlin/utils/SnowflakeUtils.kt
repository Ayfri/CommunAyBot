package utils

import dev.kord.common.entity.Snowflake

fun Int.toSnowflake() = Snowflake(this.toLong())
fun Long.toSnowflake() = Snowflake(this)
fun String.toSnowflake() = Snowflake(this)
