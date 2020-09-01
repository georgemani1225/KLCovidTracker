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
    val idukki: Idukki,
    @SerializedName("Ernakulam")
    val ernakulam: Ernakulam,
    @SerializedName("Thrissur")
    val thrissur: Thrissur,
    @SerializedName("Palakkad")
    val palakkad: Palakkad,
    @SerializedName("Malappuram")
    val malappuram: Malappuram,
    @SerializedName("Kozhikode")
    val kozhikode: Kozhikode,
    @SerializedName("Wayanad")
    val wayanad: Wayanad,
    @SerializedName("Kannur")
    val kannur: Kannur,
    @SerializedName("Kasaragod")
    val kasaragod: Kasaragod
)