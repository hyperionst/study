package com.hyperion.core

import mu.KotlinLogging

val logger = KotlinLogging.logger {} // KotlinLogging 사용

fun main() {
    logger.info { "logger running test" }
}


