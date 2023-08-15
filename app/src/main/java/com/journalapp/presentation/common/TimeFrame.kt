package com.journalapp.presentation.common

import com.journalapp.R

enum class TimeFrame(val id: Int) {
    THIS_WEEK(R.string.this_week),
    LAST_WEEK(R.string.last_week),
    LAST_MONTH(R.string.last_month),
    MORE_THAN_A_MONTH_AGO(R.string.more_than_a_month_ago),
    LAST_YEAR(R.string.last_year),
    MORE_THAN_A_YEAR_AGO(R.string.more_than_a_year_ago)
}
