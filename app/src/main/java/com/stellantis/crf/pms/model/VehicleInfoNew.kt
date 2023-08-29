package com.stellantis.crf.pms.model

import com.google.gson.annotations.SerializedName

data class VehicleInfoNew(
    @SerializedName("energies")
    val energies: List<VehicleInfo>?,
    @SerializedName("vehicles")
    val vehicles: List<VehicleInfo>?,
    @SerializedName("components")
    val components: List<ComponentsInfo>?,
)


















