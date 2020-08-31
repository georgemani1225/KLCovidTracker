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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
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
            .setGitHubUserAndRepo("georgemani1225", "KLCovidTracker")
            .setTitleOnUpdateAvailable("New Update Available!")
            .setContentOnUpdateAvailable("Check out the latest version available of app!")
            .setButtonUpdate("Update")
            .setButtonDismiss("Later")
            .setButtonDoNotShowAgain("")
            .start()

        val request = Request.Builder()
            .url("https://api.covid19india.org/data.json")
            .build()

        val api = OkHttpClient().newCall(request)

        GlobalScope.launch {
            val tResponse = withContext(Dispatchers.IO) { api.execute() }
            val data = Gson().fromJson(
                tResponse.body?.string(),
                com.gapps.klcovidtracker.Response::class.java
            )
            launch(Dispatchers.Main) {
                bindCombinedData(data?.statewise?.get(16)!!)

            }
        }


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.covid19india.org/")
            .build()

        val jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)
        val myCall: Call<CaseResponse> = jsonPlaceholderApi.getCaseResponse()

        myCall.enqueue(
            object : Callback<CaseResponse> {
                override fun onFailure(call: Call<CaseResponse>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

                override fun onResponse(
                    call: Call<CaseResponse>,
                    response: Response<CaseResponse>
                ) {
                    val caseResponse = response.body()!!
                    cTvm.text =
                        caseResponse.kerala.districtData.thiruvananthapuram.confirmed.toString()
                    aTvm.text =
                        caseResponse.kerala.districtData.thiruvananthapuram.active.toString()
                    rTvm.text =
                        caseResponse.kerala.districtData.thiruvananthapuram.recovered.toString()
                    dTvm.text =
                        caseResponse.kerala.districtData.thiruvananthapuram.deceased.toString()


                }
            })

    }

    private fun bindCombinedData(statewiseItem: StatewiseItem) {
        confirmedTv.text = statewiseItem.confirmed
        activeTv.text = statewiseItem.active
        deceasedTv.text = statewiseItem.deaths
        recoveredTv.text = statewiseItem.recovered

    }
}







