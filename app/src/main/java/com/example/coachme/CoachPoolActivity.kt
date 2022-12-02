package com.example.coachme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.SingleSpinnerListener
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch
import com.example.coachme.databinding.CoachpoolBinding
import de.hdodenhof.circleimageview.CircleImageView


class CoachPoolActivity : AppCompatActivity() {

    private lateinit var binding: CoachpoolBinding
    private lateinit var coachesAdapter :CoachPoolAdaptor
    private var avg_rating = 0.0F
    override fun onCreate(savedInstanceState: Bundle?) {
        var coaches: ArrayList<Coach> = ArrayList()
        val url:String = Coach_reg3.FLASK_URL +"get_objects_coach"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    // extracting data from each json object
                    val respObj = response.getJSONObject(i)
                    val user_id = respObj.getInt("user_id")
                    val gender = respObj.getString("gender")
                    val name = respObj.getString("name")
                    val coach_id = respObj.getInt("coach_id")
                    val yearExp = respObj.getString("yearExp")
                    val location = respObj.getString("location")
                    val qualification = respObj.getString("qualification")
                    val rating = respObj.getInt("rating")
                    val bookmark = respObj.getInt("bookmark").toString()
                    val rated_ppl = respObj.getInt("rated_ppl")
                    val expertise = respObj.getString("expertise")
                    val age = respObj.getString("age").toInt()

                    if (rated_ppl != 0) {
                        avg_rating = rating.toFloat() / rated_ppl.toFloat()
                    } else {
                        avg_rating = 0.0F
                    }
//                   //adding data to the list
                    coaches.add(Coach(user_id = user_id,coach_id =  coach_id, gender, name,image = getDrawable(R.drawable.coach2),yearExp,qualification,expertise, avg_rating.toString(), bookmark, rating,rated_ppl, location,age))


                }
                Log.d("coaches list extracted", coaches.toString())
                //if (coaches.size > 0 && intent.getStringArrayListExtra("filter_location") != null){
                Log.i("Filtering?", intent.getBooleanExtra("Filtering", false).toString())
                Log.i("filter_YearExp", intent.getIntExtra("filter_YearExp", 0).toString())
                if (coaches.size > 0 && intent.getBooleanExtra("Filtering", false) == true){
                    coaches = filterCoaches(intent, coaches)
                    Log.i("Filtered Coaches: ", coaches.toString())
                    coachesAdapter.updateData(coaches)

                    coachesAdapter.notifyDataSetChanged();
                }
                coachesAdapter.notifyDataSetChanged();

            },
            { error ->
                //Handle error
                Log.d("error of getting coach objects", error.toString())
            })
        Volley.newRequestQueue(this).add(jsonArrayRequest)


        super.onCreate(savedInstanceState)
        val user_id: Int? = intent.getIntExtra("user_id", 0)
        val student_id: Int? = intent.getIntExtra("student_id", 0)
        val username: String? = intent.getStringExtra("username")
        Log.d("received extra coach pool", "$user_id, $student_id")

//        binding and setting the content of coach pool page
        binding = DataBindingUtil.setContentView(this, R.layout.coachpool)
        postponeEnterTransition()
        val profile_image = findViewById<CircleImageView>(R.id.profile_image)
        val header_name = findViewById<TextView>(R.id.header_name)
        var intent: Intent = intent

        val currentUserFirstName = getSharedPreferences("userSharedPreference", MODE_PRIVATE)
            .getString("first_name", "")
        header_name.text = currentUserFirstName.toString()
        Log.i("set the header name!", currentUserFirstName.toString())
        //TODO: set dynamic profile pic
        profile_image.setImageResource(R.drawable.student2)

        val recyclerViewCoaches = binding.rvCoach
        recyclerViewCoaches.doOnPreDraw {
            startPostponedEnterTransition()
        }
        //val dummyCoach1 = Coach(
          //  coach_id = 100, user_id = 100,
            //image = getDrawable(R.drawable.coach1),
            //name = "Timmy",
            //yearExp = "10",
            //qualification = "Former Strongman Champion, IFBB Pro", rating = "5400", bookmark = "1200", rated_ppl = 1200)
//        val dummyCoach2 = Coach(coach_id = 2, image = getDrawable(R.drawable.coach2),name = "John",yearExp = "40 years",
//            qualification = "IFBB Pro", rating = "2.3", bookmark = "4k")
//        val dummyCoach3 = Coach(coach_id = 3,image = getDrawable(R.drawable.coach3),name = "Robert",yearExp = "20 years",
//            qualification = "NASM", rating = "3.9", bookmark = "900")
//        val dummyCoach4 = Coach(coach_id = 4, image = getDrawable(R.drawable.coach4),name = "Larry",yearExp = "30 years",
//            qualification = "CPT", rating = "4.2", bookmark = "702")
//        val dummyCoach5 = Coach(coach_id = 5,image = getDrawable(R.drawable.coach5),name = "Valencia",yearExp = "5 years",
//            qualification = "IFBB Pro", rating = "4.1", bookmark = "1k")
//        val dummyCoach6 = Coach(coach_id = 6, image = getDrawable(R.drawable.coach6),name = "Kelvin",yearExp = "8 years",
//            qualification = "IFBB Pro", rating = "3.3", bookmark = "1.4k")
//        val coaches = listOf(dummyCoach1,dummyCoach2,dummyCoach3,dummyCoach4,dummyCoach5,dummyCoach6)
        //coaches.add(dummyCoach1)
        /*val coachesAdapter = CoachPoolAdaptor(coaches, CoachPoolAdaptor.OnClickListener {
            val intent = Intent(this, coachprofile::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("username", username)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        }) */

        coachesAdapter = CoachPoolAdaptor(coaches) {
            Coach ->
            Log.d("Chosen Coach", Coach!!.name)
            Log.d("Chosen Coach ID", Coach!!.coach_id.toString())
            val intent = Intent(this, coachprofile::class.java)

            intent.putExtra("coach_name", Coach!!.name)
            intent.putExtra("coach_user_id", Coach!!.user_id)
            intent.putExtra("coach_id", Coach!!.coach_id)
            intent.putExtra("qua", Coach!!.qualification)
            intent.putExtra("yearExp", Coach!!.yearExp)
            intent.putExtra("bookmark", Coach!!.bookmark)
            intent.putExtra("rating", Coach.rating)
            intent.putExtra("rated_ppl", Coach.rated_ppl)
            intent.putExtra("expertise", Coach.expertise)
            intent.putExtra("student_first_name", currentUserFirstName)
            intent.putExtra("student_username", username)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);


        }



        recyclerViewCoaches.adapter = coachesAdapter
        recyclerViewCoaches.layoutManager = LinearLayoutManager(this)
        recyclerViewCoaches.setHasFixedSize(true)

        // Implementing sort (popularity (bookmark) desc, rating desc, yearExp desc, name ascd)
        val sortButton = findViewById<SingleSpinnerSearch>(R.id.sortbutton_coachpool)
        sortButton.isSearchEnabled = false

        val sort_options = arrayOf("Popularity","Rating","Experience","Name","Age")
        var sortArray = ArrayList<KeyPairBoolData>()
        for (i in sort_options.indices) {
            val keyPairBoolData = KeyPairBoolData()
            keyPairBoolData.id = (i + 1).toLong()
            keyPairBoolData.name = sort_options[i]
            keyPairBoolData.isSelected = false
            sortArray.add(keyPairBoolData)
        }

        sortButton.setItems(sortArray, object : SingleSpinnerListener {
            override fun onItemsSelected(selectedItem: KeyPairBoolData) {
                Log.i("Selected Item in Sorting: ",  selectedItem.name + selectedItem.id)
                var sortedCoaches: List<Coach> = coaches

                if (selectedItem.name == "Popularity" && coaches.size>0){
                    sortedCoaches =
                        sortedCoaches.sortedWith(compareByDescending {
                            it.bookmark.toInt()
                        })
                }
                else if (selectedItem.name == "Rating" && coaches.size>0){
                    sortedCoaches =
                        sortedCoaches.sortedWith(compareByDescending {
                            it.rating.toFloat()
                        })

                }
                else if (selectedItem.name == "Experience" && coaches.size>0){
                    sortedCoaches =
                        sortedCoaches.sortedWith(compareByDescending {
                            it.yearExp.split(" ")[0].toInt()
                        })
                }
                else if (selectedItem.name == "Name" && coaches.size>0){
                    sortedCoaches =
                        sortedCoaches.sortedWith(compareBy {
                            it.name
                        })
                }
                else if (selectedItem.name == "Age" && coaches.size>0){
                    sortedCoaches =
                        sortedCoaches.sortedWith(compareBy {
                            it.age
                        })
                }
                val sortedCoaches_arrayList:ArrayList<Coach> = ArrayList(sortedCoaches)
                coachesAdapter.updateData(sortedCoaches_arrayList)
                coachesAdapter.notifyDataSetChanged()
            }

            override fun onClear() {
                Toast.makeText(this@CoachPoolActivity, "Returned to default sorting", Toast.LENGTH_SHORT)
                    .show()
            }
        }
            )

        val filter_button = findViewById<Button>(R.id.filter_button)
        filter_button.setOnClickListener {
            val intent = Intent(this, CoachPoolFilter::class.java)
            intent.putExtra("student_user_id", user_id)
            intent.putExtra("student_id", student_id)
            intent.putExtra("student_first_name", currentUserFirstName)
            intent.putExtra("student_username", username)
            startActivity(intent)
        }

//        val coachInfoButton = findViewById<ImageButton>(R.id.coach_info_Button)
//        coachInfoButton.setOnClickListener {
//            val coachInfoIntent = Intent(this, coachprofile::class.java)
//            startActivity(coachInfoIntent)
//        }

    }

    private fun filterCoaches(intent: Intent, coaches: ArrayList<Coach>): ArrayList<Coach> {
        Log.i("CoachPoolActivity","FilterCoaches IS TRIGGERED")
        Log.i("before filtering",coaches.toString())
        var filteredCoaches: ArrayList<Coach> = coaches
        val filter_gender: ArrayList<String>? = intent.getStringArrayListExtra("filter_gender")
        val filter_location = intent.getStringArrayListExtra("filter_location")
        val filter_quali = intent.getStringArrayListExtra("filter_quali")
        val filter_YearExp = intent.getIntExtra("filter_YearExp",0)
        val filter_expertise = intent.getStringArrayListExtra("filter_expertise")
        Log.d("Filter Condition", "$filter_gender")

        if (filter_gender != null && filter_gender.isNotEmpty()) {
            filteredCoaches = filteredCoaches.filter { filter_gender.contains(it.gender) } as ArrayList<Coach>
            Log.i("after filtering gender", filteredCoaches.toString())
        }
        if (filter_location != null && filter_location.isNotEmpty()) {
            filteredCoaches = filteredCoaches.filter { filter_location.contains(it.location) } as ArrayList<Coach>
            Log.i("after filtering location", filteredCoaches.toString())
        }
        if (filter_YearExp != null) {
            filteredCoaches =
                filteredCoaches.filter { it.yearExp.toInt() >= filter_YearExp } as ArrayList<Coach>
            Log.i("after filtering yearexp", filteredCoaches.toString())
        }



        if (filter_expertise != null && filter_expertise.isNotEmpty()) {
            Log.i("expertise substring", coaches.get(0).expertise.split(",").toString())
            //filteredCoaches =
               // filteredCoaches.filter { filter_expertise!!.containsAll(it.expertise.split(",")) } as ArrayList<Coach>
            filteredCoaches =
                    filteredCoaches.filter { filter_expertise.intersect(it.expertise.split(",")).isNotEmpty() } as ArrayList<Coach>
            Log.i("after filtering expertise", filteredCoaches.toString())
        }

        if (filter_quali != null && filter_quali.isNotEmpty()) {
            Log.i("qualification substring", coaches.get(0).qualification.split(",").toString())
            //filteredCoaches =
                //filteredCoaches.filter { filter_expertise!!.containsAll(it.qualification.split(",")) } as ArrayList<Coach>
            filteredCoaches =
                filteredCoaches.filter { filter_quali.intersect(it.qualification.split(",")).isNotEmpty() } as ArrayList<Coach>
            Log.i("after filtering qualification", filteredCoaches.toString())
        }


        return filteredCoaches

    }


}