package io.collective

import java.time.Clock

class SimpleAgedKache {
    constructor(clock: Clock?) {
    }

    constructor() {
    }

    var cache: MutableList<MutableList<Any?>> = mutableListOf()

    fun put(key: Any?, value: Any?, retentionInMillis: Int) {
        var toPut: MutableList<Any?> = mutableListOf(key, value, retentionInMillis)
        cache.add(toPut)
    }

    fun isEmpty(): Boolean {
        return cache.size == 0
    }

    fun size(): Int {
        return cache.size
    }

    fun get(key: Any?): Any? {
        for (item in cache) {
            if (item[0] == key) {
                return item[1]
            }
        }
        return null
    }
}
