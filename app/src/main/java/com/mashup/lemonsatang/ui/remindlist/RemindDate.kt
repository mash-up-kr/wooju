package com.mashup.lemonsatang.ui.remindlist
import java.text.SimpleDateFormat
import java.util.*

interface RemindDate {

    private fun stringToDate(str : String) = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()).parse(str)

    private fun getFormat(d: String, f : SimpleDateFormat) = f.format(stringToDate(d))

    private fun getDate(d: String) = getFormat(d, SimpleDateFormat("M월 d일", Locale.getDefault()))

    fun getMonth(d: String)  = getFormat(d, SimpleDateFormat("M월", Locale.getDefault()))

    fun getWeekOfMonth(d : String) = getFormat(d, SimpleDateFormat("W", Locale.getDefault()))

    fun getRemindDate(d1 : String, d2 : String) ="${getDate(d1)} - ${getDate(d2)}"


}