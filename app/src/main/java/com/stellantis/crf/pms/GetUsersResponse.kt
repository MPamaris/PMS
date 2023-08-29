package com.stellantis.crf.pms

import com.google.gson.annotations.SerializedName
import com.stellantis.crf.pms.model.UserInfo

data class GetUsersResponse(
    @SerializedName("users")
    val users: List<UserInfo>?,
)




