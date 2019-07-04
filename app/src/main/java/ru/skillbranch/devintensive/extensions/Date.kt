package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val Minute = 60
const val Hour = 3600
const val Day = 86400

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND) :Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    var Diff = this.time - date.time
    var time =""
    var postfix =""
    var prefix = ""
    if (Diff<=1) postfix = " назад" else prefix = "через "

    when (abs(Diff) / SECOND) {
        in 0..1 -> time = "только что"
        in 1..45 -> time =  prefix+"несколько секунд$postfix"
        in 45..75 -> time = prefix+"минуту$postfix"
        in 75..45 * Minute -> time = "$prefix${TimeUnits.MINUTE.plural(abs(Diff / MINUTE))}$postfix"
        in 45 * Minute..75 * Minute -> time = prefix+"час$postfix"
        in 76 * Minute..22 * Hour -> time = "$prefix${TimeUnits.HOUR.plural(abs(Diff / HOUR))}$postfix"
        in 22 * Hour..26 * Hour -> time = prefix+"день$postfix"
        in 26 * Hour..360 * Day -> time = "$prefix${TimeUnits.DAY.plural(abs(Diff / DAY))}$postfix"
        else -> if (Diff<=1) time = "более года назад" else time ="более чем через год"
    }
    return time
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Long): String = value.asPlulars(arrayOf("секунду", "секунды", "секунд"))
    },
    MINUTE {
        override fun plural(value: Long): String = value.asPlulars(arrayOf("минуту", "минуты", "минут"))
    },
    HOUR {
        override fun plural(value: Long): String = value.asPlulars(arrayOf("час", "часа", "часов"))
    },
    DAY {
        override fun plural(value: Long): String = value.asPlulars(arrayOf("день", "дня", "дней"))
    };
    abstract fun plural(value: Long): String
}

fun Long.asPlulars(dimension:Array<String>):String = when {
        this in 5L..20L -> "$this ${dimension[2]}"
        this % 10L == 1L-> "$this ${dimension[0]}"
        this % 10L in 2L..4L -> "$this ${dimension[1]}"
        else -> "$this ${dimension[2]}"
}

fun String.truncate(value:Int = 16):String {
    val new= this
    if (new.length <  value) return new.trim()
    return new.substring(0,value).trim()+"..."
}

//val String.stripHtml
//    get() = {
//        var translitLetters = mapOf<String, String>("а" to "a",
//            "&amp" to "b",
//            )
//        val value = this
//        var rezult = ""
//        value.forEach {
//            val symbol = translitLetters.getOrDefault(it.toLowerCase().toString(), it.toString())
//            rezult += if (it.isUpperCase()) symbol.capitalize() else symbol
//        }
//    }




//"$prefix${getTimeUnitValue(timeDiffUnsigned, TimeUnits.MINUTE)}$postfix"