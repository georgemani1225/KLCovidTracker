package com.gapps.klcovidtracker.model

data class Idukki(
    val active: Int,
    val confirmed: Int,
    val deceased: Int,
    val delta: Delta,
    val notes: String,
    val recovered: Int
)