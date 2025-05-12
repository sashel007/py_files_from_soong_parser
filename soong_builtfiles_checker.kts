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

    var previousLine: MutableList<String> = mutableListOf("")

    reader.lineSequence().forEach { line ->
        args.forEach {
            if (it == "line") {
                if (line.contains(".py")) {
                    outputFile.appendText(line + "\n")
                }
            }

            if (it == "not_verbose") {
                val tokens = line.split(" ", ",", "[", "]", ":", "=", ";", "(", ")", "'", "\"", "`", "\\", "/")
                    for (token in tokens) {
                        if (token.contains(".py") && (token !in previousLine)) {
                            outputFile.appendText(token + "\n")
                        }
                    }
            }

            if (it == "file") {
                if (line.contains(".py")) {
                    val tokens = line.split(" ", ",", "[", "]", ":", "=", ";", "(", ")", "'", "\"", "`", "\\", "/")
                    for (token in tokens) {
                        if (token.contains(".py") && (token !in previousLine)) {
                            outputFile.appendText(token + "\n")
                        }
                    }
                    previousLine = tokens.toMutableList()
                }
            }
        }
    }
}

println("done!")
