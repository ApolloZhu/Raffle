package io.github.apollozhu.lottery.utils

import io.github.apollozhu.lottery.settings.LotteryPreferences
import io.github.apollozhu.lottery.utils.RandomSelector.forEachListener
import io.github.apollozhu.util.AZListenable
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.swing.event.ChangeListener
import javax.swing.event.EventListenerList

object RandomSelector : AZListenable<ChangeListener> {

    private val changeListenerList = EventListenerList()
    override fun getListenerList() = changeListenerList

    init {
        LotteryPreferences.addListener { loadList() }
        loadList()
    }

    private var list: MutableList<String> = ArrayList()

    private var currentPath = ""

    fun loadList(stringPath: String = LotteryPreferences.listPath, cs: Charset? = null) {
        val path = Paths.get(stringPath)
        if (stringPath == currentPath || !Files.exists(path)) {
            return
        }
        currentPath = stringPath
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
        forEachListener { it.stateChanged(null) }
    }

    fun add(it: String) {
        list.add(it)
    }

    fun next(): String? {
        if (hasNext()) {
            val index = (Math.random() * list.size).toInt()
            return list.removeAt(index)
        } else {
            return null
        }
    }

    fun hasNext(): Boolean {
        return list.size > 0
    }
}
