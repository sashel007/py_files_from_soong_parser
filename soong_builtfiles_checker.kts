#!/bin/sh

import java.io.File
import java.io.BufferedReader

println("Script starts!")

val parsedFile = File("f72i_soong.log")
val outputFile = File("result_with_soong.txt")

println("does file $parsedFile exist: ${parsedFile.exists()}")

try {
    val isOutputFileCreated = outputFile.createNewFile()
    println("Output file created: $isOutputFileCreated")
} catch (e: Exception) {
    println(
        """
        Error while creating output file: ${e.message}
        """.trimIndent()
    )
}

parsedFile.bufferedReader().use { reader ->
    println("parsedFile.bufferedReader()")
    reader.lineSequence().forEach { line ->
        if (line.contains(".py")) {
            println("Line contains .py file")
            outputFile.appendText(line + "\n")
            // val tokens = line.split(" ", ",", "[", "]", ":", ".", "=", ";", "(", ")", "'", "\"", "`", "\\", "/")
            // for (token in tokens) {
            //     if (token.endsWith(".py")) {
            //         outputFile.appendText(line)
            //         outputFile.appendText("\n")
            //     }
            // }
        }
    }
}

