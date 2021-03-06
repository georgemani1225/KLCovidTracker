package com.gapps.klcovidtracker

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.CalendarContract
import android.util.Log
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.gapps.klcovidtracker.model.CaseResponse
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.google.firebase.database.*
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

        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.setColorSchemeResources(R.color.dark_green)
            swipeToRefresh.isRefreshing = false
        }

        menubutton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_feedback -> {
                        val intent = Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", "georgemani1225@gmail.com", null)
                        )
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.menu_main)
            popupMenu.show()
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
                    cdTvm.text =
                        "↑ " + caseResponse.kerala.districtData.thiruvananthapuram.delta.confirmed.toString()
                    rdTvm.text =
                        "↑ " + caseResponse.kerala.districtData.thiruvananthapuram.delta.recovered.toString()
                    ddTvm.text =
                        "↑ " + caseResponse.kerala.districtData.thiruvananthapuram.delta.deceased.toString()

                    cKlm.text =
                        caseResponse.kerala.districtData.kollam.confirmed.toString()
                    aKlm.text =
                        caseResponse.kerala.districtData.kollam.active.toString()
                    rKlm.text =
                        caseResponse.kerala.districtData.kollam.recovered.toString()
                    dKlm.text =
                        caseResponse.kerala.districtData.kollam.deceased.toString()
                    cdKlm.text =
                        "↑ " + caseResponse.kerala.districtData.kollam.delta.confirmed.toString()
                    rdKlm.text =
                        "↑ " + caseResponse.kerala.districtData.kollam.delta.recovered.toString()
                    ddKlm.text =
                        "↑ " + caseResponse.kerala.districtData.kollam.delta.deceased.toString()

                    cAlp.text =
                        caseResponse.kerala.districtData.alappuzha.confirmed.toString()
                    aAlp.text =
                        caseResponse.kerala.districtData.alappuzha.active.toString()
                    rAlp.text =
                        caseResponse.kerala.districtData.alappuzha.recovered.toString()
                    dAlp.text =
                        caseResponse.kerala.districtData.alappuzha.deceased.toString()
                    cdAlp.text =
                        "↑ " + caseResponse.kerala.districtData.alappuzha.delta.confirmed.toString()
                    rdAlp.text =
                        "↑ " + caseResponse.kerala.districtData.alappuzha.delta.recovered.toString()
                    ddAlp.text =
                        "↑ " + caseResponse.kerala.districtData.alappuzha.delta.deceased.toString()

                    cPtm.text =
                        caseResponse.kerala.districtData.pathanamthitta.confirmed.toString()
                    aPtm.text =
                        caseResponse.kerala.districtData.pathanamthitta.active.toString()
                    rPtm.text =
                        caseResponse.kerala.districtData.pathanamthitta.recovered.toString()
                    dPtm.text =
                        caseResponse.kerala.districtData.pathanamthitta.deceased.toString()
                    cdPtm.text =
                        "↑ " + caseResponse.kerala.districtData.pathanamthitta.delta.confirmed.toString()
                    rdPtm.text =
                        "↑ " + caseResponse.kerala.districtData.pathanamthitta.delta.recovered.toString()
                    ddPtm.text =
                        "↑ " + caseResponse.kerala.districtData.pathanamthitta.delta.deceased.toString()

                    cKtm.text =
                        caseResponse.kerala.districtData.kottayam.confirmed.toString()
                    aKtm.text =
                        caseResponse.kerala.districtData.kottayam.active.toString()
                    rKtm.text =
                        caseResponse.kerala.districtData.kottayam.recovered.toString()
                    dKtm.text =
                        caseResponse.kerala.districtData.kottayam.deceased.toString()
                    cdKtm.text =
                        "↑ " + caseResponse.kerala.districtData.kottayam.delta.confirmed.toString()
                    rdKtm.text =
                        "↑ " + caseResponse.kerala.districtData.kottayam.delta.recovered.toString()
                    ddKtm.text =
                        "↑ " + caseResponse.kerala.districtData.kottayam.delta.deceased.toString()

                    cIdk.text =
                        caseResponse.kerala.districtData.idukki.confirmed.toString()
                    aIdk.text =
                        caseResponse.kerala.districtData.idukki.active.toString()
                    rIdk.text =
                        caseResponse.kerala.districtData.idukki.recovered.toString()
                    dIdk.text =
                        caseResponse.kerala.districtData.idukki.deceased.toString()
                    cdIdk.text =
                        "↑ " + caseResponse.kerala.districtData.idukki.delta.confirmed.toString()
                    rdIdk.text =
                        "↑ " + caseResponse.kerala.districtData.idukki.delta.recovered.toString()
                    ddIdk.text =
                        "↑ " + caseResponse.kerala.districtData.idukki.delta.deceased.toString()

                    cEkm.text =
                        caseResponse.kerala.districtData.ernakulam.confirmed.toString()
                    aEkm.text =
                        caseResponse.kerala.districtData.ernakulam.active.toString()
                    rEkm.text =
                        caseResponse.kerala.districtData.ernakulam.recovered.toString()
                    dEkm.text =
                        caseResponse.kerala.districtData.ernakulam.deceased.toString()
                    cdEkm.text =
                        "↑ " + caseResponse.kerala.districtData.ernakulam.delta.confirmed.toString()
                    rdEkm.text =
                        "↑ " + caseResponse.kerala.districtData.ernakulam.delta.recovered.toString()
                    ddEkm.text =
                        "↑ " + caseResponse.kerala.districtData.ernakulam.delta.deceased.toString()

                    cTrs.text =
                        caseResponse.kerala.districtData.thrissur.confirmed.toString()
                    aTrs.text =
                        caseResponse.kerala.districtData.thrissur.active.toString()
                    rTrs.text =
                        caseResponse.kerala.districtData.thrissur.recovered.toString()
                    dTrs.text =
                        caseResponse.kerala.districtData.thrissur.deceased.toString()
                    cdTrs.text =
                        "↑ " + caseResponse.kerala.districtData.thrissur.delta.confirmed.toString()
                    rdTrs.text =
                        "↑ " + caseResponse.kerala.districtData.thrissur.delta.recovered.toString()
                    ddTrs.text =
                        "↑ " + caseResponse.kerala.districtData.thrissur.delta.deceased.toString()

                    cPkd.text =
                        caseResponse.kerala.districtData.palakkad.confirmed.toString()
                    aPkd.text =
                        caseResponse.kerala.districtData.palakkad.active.toString()
                    rPkd.text =
                        caseResponse.kerala.districtData.palakkad.recovered.toString()
                    dPkd.text =
                        caseResponse.kerala.districtData.palakkad.deceased.toString()
                    cdPkd.text =
                        "↑ " + caseResponse.kerala.districtData.palakkad.delta.confirmed.toString()
                    rdPkd.text =
                        "↑ " + caseResponse.kerala.districtData.palakkad.delta.recovered.toString()
                    ddPkd.text =
                        "↑ " + caseResponse.kerala.districtData.palakkad.delta.deceased.toString()

                    cMlp.text =
                        caseResponse.kerala.districtData.malappuram.confirmed.toString()
                    aMlp.text =
                        caseResponse.kerala.districtData.malappuram.active.toString()
                    rMlp.text =
                        caseResponse.kerala.districtData.malappuram.recovered.toString()
                    dMlp.text =
                        caseResponse.kerala.districtData.malappuram.deceased.toString()
                    cdMlp.text =
                        "↑ " + caseResponse.kerala.districtData.malappuram.delta.confirmed.toString()
                    rdMlp.text =
                        "↑ " + caseResponse.kerala.districtData.malappuram.delta.recovered.toString()
                    ddMlp.text =
                        "↑ " + caseResponse.kerala.districtData.malappuram.delta.deceased.toString()

                    cKzd.text =
                        caseResponse.kerala.districtData.kozhikode.confirmed.toString()
                    aKzd.text =
                        caseResponse.kerala.districtData.kozhikode.active.toString()
                    rKzd.text =
                        caseResponse.kerala.districtData.kozhikode.recovered.toString()
                    dKzd.text =
                        caseResponse.kerala.districtData.kozhikode.deceased.toString()
                    cdKzd.text =
                        "↑ " + caseResponse.kerala.districtData.kozhikode.delta.confirmed.toString()
                    rdKzd.text =
                        "↑ " + caseResponse.kerala.districtData.kozhikode.delta.recovered.toString()
                    ddKzd.text =
                        "↑ " + caseResponse.kerala.districtData.kozhikode.delta.deceased.toString()

                    cWnd.text =
                        caseResponse.kerala.districtData.wayanad.confirmed.toString()
                    aWnd.text =
                        caseResponse.kerala.districtData.wayanad.active.toString()
                    rWnd.text =
                        caseResponse.kerala.districtData.wayanad.recovered.toString()
                    dWnd.text =
                        caseResponse.kerala.districtData.wayanad.deceased.toString()
                    cdWnd.text =
                        "↑ " + caseResponse.kerala.districtData.wayanad.delta.confirmed.toString()
                    rdWnd.text =
                        "↑ " + caseResponse.kerala.districtData.wayanad.delta.recovered.toString()
                    ddWnd.text =
                        "↑ " + caseResponse.kerala.districtData.wayanad.delta.deceased.toString()

                    cKnr.text =
                        caseResponse.kerala.districtData.kannur.confirmed.toString()
                    aKnr.text =
                        caseResponse.kerala.districtData.kannur.active.toString()
                    rKnr.text =
                        caseResponse.kerala.districtData.kannur.recovered.toString()
                    dKnr.text =
                        caseResponse.kerala.districtData.kannur.deceased.toString()
                    cdKnr.text =
                        "↑ " + caseResponse.kerala.districtData.kannur.delta.confirmed.toString()
                    rdKnr.text =
                        "↑ " + caseResponse.kerala.districtData.kannur.delta.recovered.toString()
                    ddKnr.text =
                        "↑ " + caseResponse.kerala.districtData.kannur.delta.deceased.toString()

                    cKsd.text =
                        caseResponse.kerala.districtData.kasaragod.confirmed.toString()
                    aKsd.text =
                        caseResponse.kerala.districtData.kasaragod.active.toString()
                    rKsd.text =
                        caseResponse.kerala.districtData.kasaragod.recovered.toString()
                    dKsd.text =
                        caseResponse.kerala.districtData.kasaragod.deceased.toString()
                    cdKsd.text =
                        "↑ " + caseResponse.kerala.districtData.kasaragod.delta.confirmed.toString()
                    rdKsd.text =
                        "↑ " + caseResponse.kerala.districtData.kasaragod.delta.recovered.toString()
                    ddKsd.text =
                        "↑ " + caseResponse.kerala.districtData.kasaragod.delta.deceased.toString()

                    confirmedTv.text =
                        (caseResponse.kerala.districtData.thiruvananthapuram.confirmed + caseResponse.kerala.districtData.kollam.confirmed + caseResponse.kerala.districtData.alappuzha.confirmed + caseResponse.kerala.districtData.pathanamthitta.confirmed + caseResponse.kerala.districtData.kottayam.confirmed + caseResponse.kerala.districtData.idukki.confirmed + caseResponse.kerala.districtData.ernakulam.confirmed + caseResponse.kerala.districtData.thrissur.confirmed + caseResponse.kerala.districtData.palakkad.confirmed + caseResponse.kerala.districtData.malappuram.confirmed + caseResponse.kerala.districtData.kozhikode.confirmed + caseResponse.kerala.districtData.wayanad.confirmed + caseResponse.kerala.districtData.kannur.confirmed + caseResponse.kerala.districtData.kasaragod.confirmed).toString()
                    activeTv.text =
                        (caseResponse.kerala.districtData.thiruvananthapuram.active + caseResponse.kerala.districtData.kollam.active + caseResponse.kerala.districtData.alappuzha.active + caseResponse.kerala.districtData.pathanamthitta.active + caseResponse.kerala.districtData.kottayam.active + caseResponse.kerala.districtData.idukki.active + caseResponse.kerala.districtData.ernakulam.active + caseResponse.kerala.districtData.thrissur.active + caseResponse.kerala.districtData.palakkad.active + caseResponse.kerala.districtData.malappuram.active + caseResponse.kerala.districtData.kozhikode.active + caseResponse.kerala.districtData.wayanad.active + caseResponse.kerala.districtData.kannur.active + caseResponse.kerala.districtData.kasaragod.active).toString()
                    recoveredTv.text =
                        (caseResponse.kerala.districtData.thiruvananthapuram.recovered + caseResponse.kerala.districtData.kollam.recovered + caseResponse.kerala.districtData.alappuzha.recovered + caseResponse.kerala.districtData.pathanamthitta.recovered + caseResponse.kerala.districtData.kottayam.recovered + caseResponse.kerala.districtData.idukki.recovered + caseResponse.kerala.districtData.ernakulam.recovered + caseResponse.kerala.districtData.thrissur.recovered + caseResponse.kerala.districtData.palakkad.recovered + caseResponse.kerala.districtData.malappuram.recovered + caseResponse.kerala.districtData.kozhikode.recovered + caseResponse.kerala.districtData.wayanad.recovered + caseResponse.kerala.districtData.kannur.recovered + caseResponse.kerala.districtData.kasaragod.recovered).toString()
                    deceasedTv.text =
                        (caseResponse.kerala.districtData.thiruvananthapuram.deceased + caseResponse.kerala.districtData.kollam.deceased + caseResponse.kerala.districtData.alappuzha.deceased + caseResponse.kerala.districtData.pathanamthitta.deceased + caseResponse.kerala.districtData.kottayam.deceased + caseResponse.kerala.districtData.idukki.deceased + caseResponse.kerala.districtData.ernakulam.deceased + caseResponse.kerala.districtData.thrissur.deceased + caseResponse.kerala.districtData.palakkad.deceased + caseResponse.kerala.districtData.malappuram.deceased + caseResponse.kerala.districtData.kozhikode.deceased + caseResponse.kerala.districtData.wayanad.deceased + caseResponse.kerala.districtData.kannur.deceased + caseResponse.kerala.districtData.kasaragod.deceased).toString()

                    dconfirmedTv.text =
                        "↑ " + (caseResponse.kerala.districtData.thiruvananthapuram.delta.confirmed + caseResponse.kerala.districtData.kollam.delta.confirmed + caseResponse.kerala.districtData.alappuzha.delta.confirmed + caseResponse.kerala.districtData.pathanamthitta.delta.confirmed + caseResponse.kerala.districtData.kottayam.delta.confirmed + caseResponse.kerala.districtData.idukki.delta.confirmed + caseResponse.kerala.districtData.ernakulam.delta.confirmed + caseResponse.kerala.districtData.thrissur.delta.confirmed + caseResponse.kerala.districtData.palakkad.delta.confirmed + caseResponse.kerala.districtData.malappuram.delta.confirmed + caseResponse.kerala.districtData.kozhikode.delta.confirmed + caseResponse.kerala.districtData.wayanad.delta.confirmed + caseResponse.kerala.districtData.kannur.delta.confirmed + caseResponse.kerala.districtData.kasaragod.delta.confirmed).toString()
                    drecoveredTv.text =
                        "↑ " + (caseResponse.kerala.districtData.thiruvananthapuram.delta.recovered + caseResponse.kerala.districtData.kollam.delta.recovered + caseResponse.kerala.districtData.alappuzha.delta.recovered + caseResponse.kerala.districtData.pathanamthitta.delta.recovered + caseResponse.kerala.districtData.kottayam.delta.recovered + caseResponse.kerala.districtData.idukki.delta.recovered + caseResponse.kerala.districtData.ernakulam.delta.recovered + caseResponse.kerala.districtData.thrissur.delta.recovered + caseResponse.kerala.districtData.palakkad.delta.recovered + caseResponse.kerala.districtData.malappuram.delta.recovered + caseResponse.kerala.districtData.kozhikode.delta.recovered + caseResponse.kerala.districtData.wayanad.delta.recovered + caseResponse.kerala.districtData.kannur.delta.recovered + caseResponse.kerala.districtData.kasaragod.delta.recovered).toString()
                    ddeceasedTv.text =
                        "↑ " + (caseResponse.kerala.districtData.thiruvananthapuram.delta.deceased + caseResponse.kerala.districtData.kollam.delta.deceased + caseResponse.kerala.districtData.alappuzha.delta.deceased + caseResponse.kerala.districtData.pathanamthitta.delta.deceased + caseResponse.kerala.districtData.kottayam.delta.deceased + caseResponse.kerala.districtData.idukki.delta.deceased + caseResponse.kerala.districtData.ernakulam.delta.deceased + caseResponse.kerala.districtData.thrissur.delta.deceased + caseResponse.kerala.districtData.palakkad.delta.deceased + caseResponse.kerala.districtData.malappuram.delta.deceased + caseResponse.kerala.districtData.kozhikode.delta.deceased + caseResponse.kerala.districtData.wayanad.delta.deceased + caseResponse.kerala.districtData.kannur.delta.deceased + caseResponse.kerala.districtData.kasaragod.delta.deceased).toString()

                }
            })

    }
}







