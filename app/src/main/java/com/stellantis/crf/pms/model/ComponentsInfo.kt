package com.stellantis.crf.pms.model

import com.google.gson.annotations.SerializedName

data class ComponentsInfo(
    @SerializedName("componentName")
    val componentName: String?,
    @SerializedName("health")
    val health: String?,
    @SerializedName("expected_breakdown")
    val expectedBreakdown: String?,
)


















