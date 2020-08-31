package com.gapps.klcovidtracker.model


import com.google.gson.annotations.SerializedName

data class CaseResponse(
    @SerializedName("Kerala")
    val kerala: Kerala
)