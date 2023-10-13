package com.stellantis.crf.pms.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgumentsToDetailsPageInfo(
    val argumentsPassed: String,
    val inCriticalFromHome: String,
) : Parcelable