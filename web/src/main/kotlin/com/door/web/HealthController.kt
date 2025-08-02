package com.door.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/health")
    fun health() = "up"

    @GetMapping("/exception")
    fun exception() = IllegalArgumentException("예외 발생!")
}
