package edu.spring.btp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BtpApplication

fun main(args: Array<String>) {
    runApplication<BtpApplication>(*args)
}
