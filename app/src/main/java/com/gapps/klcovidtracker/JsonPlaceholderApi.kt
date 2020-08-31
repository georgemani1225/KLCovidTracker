package com.gapps.klcovidtracker

import com.gapps.klcovidtracker.model.CaseResponse
import com.gapps.klcovidtracker.model.Kerala
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("state_district_wise.json")
    fun getCaseResponse(): Call<CaseResponse>
}