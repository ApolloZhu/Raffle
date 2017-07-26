package io.github.apollozhu.lottery

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

object RandomSelector: Iterator<String> {

    var list: MutableList<String> = ArrayList()

    fun loadList(path: Path, cs: Charset? = null) {
        var stream: Stream<String>
        try {
            stream = Files.lines(path, cs ?: Charset.forName("GBK"))
        } catch (ignored: Exception) {
            stream = Files.lines(path)
        }
        list = stream
                .map { it.replace("\\s+", "") }
                .filter { !it.isBlank() }
                .collect(Collectors.toList())
    }

    override fun next(): String {
        val index = (Math.random() * list.size).toInt()
        return list.removeAt(index)
    }

    override fun hasNext(): Boolean {
        return list.size > 0
    }
}
