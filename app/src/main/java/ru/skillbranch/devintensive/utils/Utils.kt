package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname:String?):Pair<String?,String?>{
        val parts : List<String>? = fullname?.split(" ")

        var firstName =parts?.getOrNull(0)
        var lastName =parts?.getOrNull(1)
        return firstName to lastName
    }
}