package com.stellantis.crf.pms

import com.google.gson.annotations.SerializedName

data class NotificationsInfo(
    @SerializedName("vin")
    val vin: String?,
    @SerializedName("unreadNotification")
    val unreadNotification: String?,
    @SerializedName("notificationTitle")
    val notificationTitle: String?,
    @SerializedName("notificationText")
    val notificationText: String?,
    @SerializedName("notificationType")
    val notificationType: String?,
    @SerializedName("notificationSeverity")
    val notificationSeverity: String?,
)






