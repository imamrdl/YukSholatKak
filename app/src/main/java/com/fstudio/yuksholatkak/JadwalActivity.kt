package com.fstudio.yuksholatkak

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fstudio.Yuksholatkak.networking.ClientAsyncTask
import com.fstudio.Yuksholatkak.networking.DaftarKota
import kotlinx.android.synthetic.main.activity_jadwal.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class JadwalActivity : AppCompatActivity() {

    private var listDaftarKota: MutableList<DaftarKota>? = null
    private var mDaftarKotaAdapter: ArrayAdapter<DaftarKota>? = null
    private var greetImg: ImageView? = null
    private var greetText: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Jadwal Sholat"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val mStartActBtn1 = findViewById<Button>(R.id.feedbackbutton)
        //handle button click
        mStartActBtn1.setOnClickListener {
            //start activity intent
            startActivity(Intent(this@JadwalActivity, FeedbackActivity::class.java))
        }

        greetImg = findViewById(R.id.greeting_img)
        greetText = findViewById(R.id.greeting_text)

        listDaftarKota = ArrayList()
        mDaftarKotaAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            listDaftarKota as ArrayList<DaftarKota>
        )
        mDaftarKotaAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kota.adapter = mDaftarKotaAdapter
        kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            @RequiresApi(Build.VERSION_CODES.CUPCAKE)
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val kota = mDaftarKotaAdapter!!.getItem(position)
                loadJadwal(kota!!.id)
            }

        }

        loadKota()
        greeting()
    }

    @SuppressLint("SetTextI18n")
    private fun greeting() {
        val calendar = Calendar.getInstance()
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay >= 0 && timeOfDay < 4) {
            greetText?.setText("Halo, jangan lupa sepertiga malamnya ya!")
            greetImg?.setImageResource(R.drawable.img_default_half_night)
        } else if (timeOfDay >= 4 && timeOfDay < 12) {
            greetText?.setText("Selamat Pagi, sudah sholat Subuhnya?")
            greetImg?.setImageResource(R.drawable.img_default_half_morning)
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText?.setText("Selamat Siang, kakak udah belum sholat Zuhur?")
            greetImg?.setImageResource(R.drawable.img_default_half_afternoon)
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText?.setText("Selamat Sore, Asharan udah belum kak?")
            greetImg?.setImageResource(R.drawable.img_default_half_afternoon)
        } else if (timeOfDay >= 18 && timeOfDay < 19) {
            greetText?.setText("Selamat Malam, sudah sholat Maghrib belum kak?")
            greetText?.setTextColor(Color.BLACK)
            greetImg?.setImageResource(R.drawable.img_default_half_without_sun)
        } else if (timeOfDay >= 19 && timeOfDay < 24) {
            greetText?.setText("Selamat Malam, jangan lupa sholat Isya")
            greetText?.setTextColor(Color.BLACK)
            greetImg?.setImageResource(R.drawable.img_default_half_night)
        }
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("SimpleDateFormat")
    private fun loadJadwal(id: Int?) {
        try {
            val idKota = id.toString()

            val current = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = current.format(Date())

            val url = "https://api.banghasan.com/sholat/format/json/jadwal/kota/$idKota/tanggal/$tanggal"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("JadwalData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val objJadwal = jsonObj.getJSONObject("jadwal")
                        val obData = objJadwal.getJSONObject("data")

                        tv_tanggal.text = obData.getString("tanggal")
                        tv_subuh.text = obData.getString("subuh")
                        tv_dzuhur.text = obData.getString("dzuhur")
                        tv_ashar.text = obData.getString("ashar")
                        tv_maghrib.text = obData.getString("maghrib")
                        tv_isya.text = obData.getString("isya")

                        Log.d("dataJadwal", obData.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun loadKota() {
        try {
            val url = "https://api.banghasan.com/sholat/format/json/kota"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("KotaData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val jsonArray = jsonObj.getJSONArray("kota")
                        var daftarKota: DaftarKota?
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            daftarKota = DaftarKota()
                            daftarKota.id = obj.getInt("id")
                            daftarKota.nama = obj.getString("nama")
                            listDaftarKota!!.add(daftarKota)
                        }
                        mDaftarKotaAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}
