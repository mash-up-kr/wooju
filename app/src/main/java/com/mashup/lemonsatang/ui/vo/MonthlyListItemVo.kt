package com.mashup.lemonsatang.ui.vo

data class MonthlyListItemVo(
    var day : Int,
    var isDataSet: Boolean // 데이터 들어가야됌. 지금은 임시로 데이터가 있는지 없는지 체크하는 불린값으로만 줌.
)