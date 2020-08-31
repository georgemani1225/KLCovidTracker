package com.gapps.klcovidtracker

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import com.gapps.klcovidtracker.model.CaseResponse
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppUpdater(this)
            .setDisplay(Display.DIALOG)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setGitHubUserAndRepo("georgemani1225", "KLCovid Tracker")
            .setTitleOnUpdateAvailable("New Update Available!")
            .setButtonUpdate("Update")
            .setButtonDismiss("Later")
            .start()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.covid19india.org/")
            .build()

        val jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)
        val myCall: Call<CaseResponse> = jsonPlaceholderApi.getCaseResponse()

        myCall.enqueue(object: Callback<CaseResponse>{
            override fun onFailure(call: Call<CaseResponse>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<CaseResponse>, response: Response<CaseResponse>) {
                val caseResponse = response.body()!!
                cTvm.text = caseResponse.kerala.districtData.thiruvananthapuram.confirmed.toString()
                aTvm.text = caseResponse.kerala.districtData.thiruvananthapuram.active.toString()
                rTvm.text = caseResponse.kerala.districtData.thiruvananthapuram.recovered.toString()
                dTvm.text = caseResponse.kerala.districtData.thiruvananthapuram.deceased.toString()


            }
        })

    }
}







