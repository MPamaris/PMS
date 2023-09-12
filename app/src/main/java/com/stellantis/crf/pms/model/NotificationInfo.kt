package com.stellantis.crf.pms.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationInfo(
    val isNotify: String,
    val vehicleSelected: String,
) : Parcelable