package com.gapps.klcovidtracker.model


import com.google.gson.annotations.SerializedName

data class Delta(
    val confirmed: Int,
    val deceased: Int,
    val recovered: Int
)