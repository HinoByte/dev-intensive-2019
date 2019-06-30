package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname:String?):Pair<String?,String?>{
        if (fullname != null && !fullname.isEmpty()){
            val parts:List<String> = fullname.trim().split(" ")

            var firstName: String? = parts.getOrNull(0)
            var lastName:String? =parts.getOrNull(1)

            return when {
                firstName == "" -> Pair(null, lastName)
                lastName == "" -> Pair(firstName, null)
                else -> Pair(firstName, lastName)
            }
        }
            return Pair(null,null)
    }
}