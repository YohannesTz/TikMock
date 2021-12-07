package com.yohannes.dev.app.tikmock
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.yohannes.dev.app.tikmock.data.VideoItem
import com.yohannes.dev.app.tikmock.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val videoItems = ArrayList<VideoItem>()
        videoItems.addAll(getVideoItems())*/

        val listVideo = ArrayList<VideoItem>()
        Log.e("FIREBASE", "ABOUT TO INVOKE FIREBASE")
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("videos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.e("FIREBASE","${document.toObject(VideoItem::class.java)}")
                    listVideo.add(document.toObject(VideoItem::class.java))
                }
                Log.e("FIREBASESIZE2", listVideo.size.toString())
                binding.videosViewPager.adapter = VideoAdapter(listVideo)
            }
            .addOnFailureListener { exception ->
                Log.e("FIREBASE", "Error getting documents: ", exception)
            }
    }
}