package com.stellantis.crf.pms.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("userid")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("surname")
    val surname: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("contesto")
    val contesto: String?,
    @SerializedName("roles")
    val roles: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("creationts")
    val creationTimestamp: Long?,
    @SerializedName("lastupdatets")
    val lastUpdateTimestamp: Long?,
)






