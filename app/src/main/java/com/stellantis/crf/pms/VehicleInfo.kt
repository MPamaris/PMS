package com.stellantis.crf.pms

import com.google.gson.annotations.SerializedName

/*data class VehicleInfo(
    @SerializedName("vehicleid")
    val vehicleId: String?,
    @SerializedName("vin")
    val vin: String?,
    @SerializedName("plate")
    val plate: String?,
    @SerializedName("modelid")
    val modelId: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("contesto")
    val contesto: String?,
    @SerializedName("status")
    val status: Int?,
)*/
data class VehicleInfo(
    @SerializedName("type")
    val type: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("autonomy")
    val autonomy: String?,
    @SerializedName("model")
    val model: String?,
)


















