package com.stellantis.crf.pms

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    @SerializedName("users")
    val users: List<UserInfo>?,
)




