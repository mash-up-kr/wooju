package com.mashup.lemonsatang.ui.remindlist
import java.text.SimpleDateFormat
import java.util.*

interface RemindListDate {

    private fun getString(str: String) = str.substring(0,10)

    private fun stringToDate(str : String) : Date{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.parse(getString(str))
    }

    private fun dateToFormat(f : Date) : String{
        val format = SimpleDateFormat("MM.dd",  Locale.ENGLISH)
        return format.format(f)
    }

    private fun getFormat(date: String) = dateToFormat(stringToDate(date))

    fun getMonth(date : String)  = getFormat(date).substring(0,2)

    fun weekOfMonth(str : String) : Int {
        val c = Calendar.getInstance()
        c.time = stringToDate(str)
        val wm = c.get(Calendar.WEEK_OF_MONTH)
        return wm

    }
    fun getRemindDate(d1 : String, d2 : String) ="${getFormat(d1)} - ${getFormat(d2)}"


}