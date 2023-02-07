package com.example.myapplication
import kotlin.math.pow

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val reposts: Int = 0,
    val likedByMe: Boolean = false
) {
    fun numberOfReactrion(count: Int): String {
        var print: String
        var length: Int = count.toString().length - 1
        if (count < 1000) print = count.toString() else
            if (count == 1000) print = "1k" else
                if (count == 1000_000) print = "1m" else {
                    var rezult = count / 10.0.pow(length)
                    if (length == 3) {
                        var format = String.format("%.1f", rezult)
                        print = "$format k"
                    } else if (length == 4 && length == 5) {
                        var format = rezult.toInt()
                        print = "$format k"
                    } else {
                        var format = String.format("%.1f", rezult)
                        print = "$format m"
                    }
                }
        return print
    }
}
