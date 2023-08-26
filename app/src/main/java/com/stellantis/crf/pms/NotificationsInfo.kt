package com.stellantis.crf.pms

import com.google.gson.annotations.SerializedName

data class NotificationsInfo(
    @SerializedName("vin")
    val vin: String?,
    @SerializedName("_comment1")
    val _comment1: String?,
    @SerializedName("unreadNotification")
    val unreadNotification: String?,
    @SerializedName("notificationTitle")
    val notificationTitle: String?,
    @SerializedName("notificationText")
    val notificationText: String?,
    @SerializedName("notificationType")
    val notificationType: String?,
    @SerializedName("_comment2")
    val _comment2: String?,
    @SerializedName("notificationSeverity")
    val notificationSeverity: String?,
)






