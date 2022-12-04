package com.example.coachme

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerListener
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.jaygoo.widget.RangeSeekBar
import de.hdodenhof.circleimageview.CircleImageView


class StudentPoolFilterActivity : AppCompatActivity()  {

    private var genders: ArrayList<String> = arrayListOf()
    private var locations: ArrayList<String> = arrayListOf()
    private var goals: ArrayList<String> = arrayListOf()
    private lateinit var districtsArrayList: ArrayList<KeyPairBoolData>
    private lateinit var goalsArrayList: ArrayList<KeyPairBoolData>
    var femaleCheck = 1
    var maleCheck = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.studentpool_filter)
        var maleButton: Button = findViewById(R.id.male_button)
        var femaleButton: Button = findViewById(R.id.female_button)
        val locationSpinner = findViewById<MultiSpinnerSearch>(R.id.location_spinner)
        val goalSpinner = findViewById<MultiSpinnerSearch>(R.id.goal_spinner)
        val experienceRangeSeekBar = findViewById<RangeSeekBar>(R.id.range_seekbar_experience)
        val payRangeSeekBar = findViewById<RangeSeekBar>(R.id.range_seekbar_pay)
        val filterButton: ImageButton = findViewById(R.id.filter_button)
        val header_name = findViewById<TextView>(R.id.header_name)
        val header_image = findViewById<CircleImageView>(R.id.profile_image)
        header_image.setImageResource(R.drawable.coach19_female)

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        locationSpinner.isSearchEnabled = true;

        // A text that will display in search hint.
        locationSpinner.setSearchHint("Search districts");

        // Set text that will display when search result not found...
        locationSpinner.setEmptyTitle("No District Found!");

        // If you will set the limit, this button will not display automatically.
        locationSpinner.isShowSelectAllButton = true;


        val location_options = resources.getStringArray(R.array.District)
        districtsArrayList = ArrayList<KeyPairBoolData>()
        for (i in location_options.indices) {
            val keyPairBoolData = KeyPairBoolData()
            keyPairBoolData.id = (i + 1).toLong()
            keyPairBoolData.name = location_options[i]
            keyPairBoolData.isSelected = false
            districtsArrayList.add(keyPairBoolData)
        }


        locationSpinner.setItems(districtsArrayList,
            MultiSpinnerListener { items ->
                for (i in items.indices) {
                    if (items[i].isSelected) {
//                        locations.add(items[i].name)
//                        Log.i("Status of the location set",locationSpinner.selectedItems.toString())
                    }
                }
            })
//--------------------------------------------
        goalSpinner.isSearchEnabled = false;
        goalSpinner.isShowSelectAllButton = true;

        val goalOptions = resources.getStringArray(R.array.Goal)
        goalsArrayList = ArrayList<KeyPairBoolData>()
        for (i in goalOptions.indices) {
            val keyPairBoolData = KeyPairBoolData()
            keyPairBoolData.id = (i + 1).toLong()
            keyPairBoolData.name = goalOptions[i]
            keyPairBoolData.isSelected = false
            goalsArrayList.add(keyPairBoolData)
        }


        goalSpinner.setItems(goalsArrayList
        ) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                }
            }
        }
//---------------------------------------------

        //should be able to select both of them
        maleButton.setOnClickListener {
            if (maleCheck == 1) {
                maleButton.setBackgroundResource(R.drawable.radio_selected)
                maleButton.setTextColor(Color.parseColor("#ffffff"))
                maleCheck = 0
                genders.add("male")
            } else {
                maleButton.setBackgroundResource(R.drawable.radio_normal)
                maleButton.setTextColor(Color.parseColor("#000000"))
                maleCheck = 1
                if (genders.contains("male")){
                    genders.remove("male")
                }
            }
        }
        femaleButton.setOnClickListener {
            if (femaleCheck == 1) {
                femaleButton.setBackgroundResource(R.drawable.radio_selected)
                femaleButton.setTextColor(Color.parseColor("#ffffff"))
                femaleCheck = 0
                genders.add("female")
            } else {
                femaleButton.setBackgroundResource(R.drawable.radio_normal)
                femaleButton.setTextColor(Color.parseColor("#000000"))
                femaleCheck = 1
                if (genders.contains("female")){
                    genders.remove("female")
                }
            }
        }
//-------------------

        experienceRangeSeekBar.setIndicatorTextDecimalFormat("0");
        experienceRangeSeekBar.setProgress(0F, 2F)

        payRangeSeekBar.setIndicatorTextDecimalFormat("0");
        payRangeSeekBar.setProgress(0f, 1000f)

        var intent: Intent = intent
        val currentUserFirstName = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
            .getString("first_name", "")
        header_name.text = currentUserFirstName.toString()

        filterButton.setOnClickListener {
            val intent =  Intent(this, StudentPoolActivity::class.java)
            for (item in locationSpinner.selectedItems){
                locations.add(item.name)
            }
            for (item in goalSpinner.selectedItems){
                goals.add(item.name)
            }
            Log.i("filter_info", "${locations.toString()}, ${goals.toString()}")
            intent.putExtra("filter_gender", genders)
            intent.putExtra("filter_location", locations)
            intent.putExtra("filter_goal", goals)
            intent.putExtra("filter_experience_lower", experienceRangeSeekBar.leftSeekBar.progress.toInt())
            intent.putExtra("filter_experience_upper", experienceRangeSeekBar.rightSeekBar.progress.toInt())
            intent.putExtra("filter_pay_lower", payRangeSeekBar.leftSeekBar.progress.toInt())
            intent.putExtra("filter_pay_upper", payRangeSeekBar.rightSeekBar.progress.toInt())

            Log.i("FILTERS-genders", genders.toString())
            Log.i("FILTERS-locations", locations.toString())
            Log.i("FILTERS-goals", goals.toString())
            Log.i("FILTERS-lower exp", experienceRangeSeekBar.leftSeekBar.progress.toString())
            Log.i("FILTERS-upper exp", experienceRangeSeekBar.rightSeekBar.progress.toString())
            Log.i("FILTERS-lower pay", payRangeSeekBar.leftSeekBar.progress.toString())
            Log.i("FILTERS-upper pay", payRangeSeekBar.rightSeekBar.progress.toString())

            startActivity(intent)
        }

    }
}