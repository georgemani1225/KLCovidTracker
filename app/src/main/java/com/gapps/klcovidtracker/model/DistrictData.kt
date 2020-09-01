package com.gapps.klcovidtracker.model


import com.google.gson.annotations.SerializedName

data class DistrictData(
    @SerializedName("Thiruvananthapuram")
    val thiruvananthapuram: Thiruvananthapuram,
    @SerializedName("Kollam")
    val kollam: Kollam,
    @SerializedName("Alappuzha")
    val alappuzha: Alappuzha,
    @SerializedName("Pathanamthitta")
    val pathanamthitta: Pathanamthitta,
    @SerializedName("Kottayam")
    val kottayam: Kottayam,
    @SerializedName("Idukki")
    val idukki: Idukki

)