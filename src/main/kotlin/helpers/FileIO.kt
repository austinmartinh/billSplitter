package helpers

import java.io.*

fun write( fileName: String, data: String) {
    val file = File(fileName)
    try {
        val outputStreamWriter = OutputStreamWriter(FileOutputStream(file))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        println("Cannot read file: $e")
    }
}

fun read(file: String): String {
    var str = ""
    try {
        val inputStreamReader = InputStreamReader(FileInputStream(file))
        if (inputStreamReader != null) {
            val bufferedReader = BufferedReader(inputStreamReader)
            val partialStr = StringBuilder()
            var done = false
            while (!done) {
                var line = bufferedReader.readLine()
                done = (line == null);
                if (line != null) partialStr.append(line);
            }
            inputStreamReader.close()
            str = partialStr.toString()
        }
    } catch (e: FileNotFoundException) {
        println("Cannot read file: $e")
    } catch (e: IOException) {
        println("Cannot read file: $e")    }
    return str
}

fun exists(fileName: String): Boolean {
    val file = File(fileName)
    return file.exists()
}