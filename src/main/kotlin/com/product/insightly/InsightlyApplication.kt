package com.product.insightly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InsightlyApplication

fun main(args: Array<String>) {
	runApplication<InsightlyApplication>(*args)
}
