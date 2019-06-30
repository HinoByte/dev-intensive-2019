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
    if (Diff<=1)
    when (abs(Diff) / SECOND) {
        in 0..1 -> time = "только что"
        in 1..45 -> time = "несколько секунд назад"
        in 45..75 -> time = "минуту назад"
        in 75..45 * Minute -> time = "${minutesAsPlulars(abs(Diff / MINUTE))} назад"
        in 45 * Minute..75 * Minute -> time = "час назад"
        in 76 * Minute..22 * Hour -> time = "${hoursAsPlulars(abs(Diff/ HOUR))} назад"
        in 22 * Hour..26 * Hour -> time = "день назад"
        in 26 * Hour..360 * Day -> time = "${daysAsPlulars(abs(Diff/ DAY))} назад"
        else -> time = "более года назад"
    }
    else when (abs(Diff) / SECOND) {
        in 0..1 -> time = "только что"
        in 1..45 -> time = "через несколько секунд"
        in 45..75 -> time = "через минуту"
        in 75..45 * Minute -> time = "через ${minutesAsPlulars(abs(Diff/ MINUTE))}"
        in 45 * Minute..75 * Minute -> time = "через час"
        in 76 * Minute..22 * Hour -> time = "через ${hoursAsPlulars(abs(Diff/ HOUR))}"
        in 22 * Hour..26 * Hour -> time = "через день"
        in 26 * Hour..360 * Day -> time = "через ${daysAsPlulars(abs(Diff/ DAY))}"
        else -> time = "более чем через год"
    }
    return time
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}

enum class Plulars{
    ONE,
    FEW,
    MANY
}

private fun minutesAsPlulars(value: Long) = when (value.asPlulars){
    Plulars.ONE -> "$value минуту"
    Plulars.FEW -> "$value минуты"
    Plulars.MANY-> "$value минут"
}
private fun hoursAsPlulars(value: Long) = when (value.asPlulars){
    Plulars.ONE -> "$value час"
    Plulars.FEW -> "$value часа"
    Plulars.MANY-> "$value часов"
}
private fun daysAsPlulars(value: Long) = when (value.asPlulars){
    Plulars.ONE -> "$value день"
    Plulars.FEW -> "$value дня"
    Plulars.MANY-> "$value дней"
}

val Long.asPlulars
    get() = when{
        this in 5L..20L -> Plulars.MANY
        this % 10L == 1L -> Plulars.ONE
        this % 10L in 2L..4L -> Plulars.FEW
        else -> Plulars.MANY
    }
//"$prefix${getTimeUnitValue(timeDiffUnsigned, TimeUnits.MINUTE)}$postfix"