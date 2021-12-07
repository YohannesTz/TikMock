package com.yohannes.dev.app.tikmock

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ui.PlayerView
import com.yohannes.dev.app.tikmock.data.VideoItem
import de.hdodenhof.circleimageview.CircleImageView

class VideoAdapter(private val videoItems: List<VideoItem>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoAdapter.VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = videoItems.size

    override fun onBindViewHolder(holder: VideoAdapter.VideoViewHolder, position: Int) {
        val url = videoItems[position].videoUrl
        val urlResource = holder.itemView.context.resources.getIdentifier(
            url,
            "raw",
            holder.itemView.context.packageName
        )

        //val path = "android.resource://" + holder.itemView.context.packageName + "/" + urlResource
        //holder.videoView.setVideoPath(path)
        //var path = "https://assets.mixkit.co/videos/preview/mixkit-waves-in-the-water-1164-large.mp4"
        holder.videoView.setVideoURI(Uri.parse(videoItems[position].videoUrl))

        holder.videoView.setOnPreparedListener { mp ->
            holder.progressBar.visibility = View.GONE
            mp.start()

            val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
            val screenRatio = holder.videoView.width / holder.videoView.height.toFloat()
            val scale = videoRatio / screenRatio

            if (scale >= 1f) {
                holder.videoView.scaleX = scale
            } else {
                holder.videoView.scaleY = 1f / scale
            }
        }

        holder.videoView.setOnCompletionListener { mp -> mp.start() }

        holder.username.text = "@" + videoItems[position].userName
        holder.caption.text = videoItems[position].caption
        holder.musicTitle.text = videoItems[position].musicTitle
        holder.musicTitle.isSelected = videoItems[position].musicBool.toBoolean()
        holder.likes.text = videoItems[position].likes
        holder.comments.text = videoItems[position].comments
        holder.forward.text = videoItems[position].forwards

        val userImg = videoItems[position].userImage
        val imgResources = holder.itemView.context.resources.getIdentifier(
            userImg,
            null,
            holder.itemView.context.packageName
        )

        Glide.with(holder.itemView.context).load(imgResources).into(holder.userImage)
        Glide.with(holder.itemView.context).load(imgResources).into(holder.musicImage)

        val isChecked = videoItems[position].isChecked.toBoolean()
        if (isChecked) {
            holder.checkedImage.visibility = View.VISIBLE
        } else {
            holder.checkedImage.visibility = View.GONE
        }

        holder.imageButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(holder.imageButton.context)

            alertDialog.apply {
                setTitle("About")
                setMessage("This App was developed by YohannesTz.\n" +
                        "\n https://yohannesTz.github.io")

                setNegativeButton("Okay") { _, _ ->
                    Toast.makeText(context, "clicked positive button", Toast.LENGTH_SHORT);

                }
            }.create().show()
        }
    }

    override fun onViewAttachedToWindow(holder: VideoViewHolder) {
        super.onViewAttachedToWindow(holder)
        val rotation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.rotate)
        rotation.interpolator = LinearInterpolator()
        holder.musicImage.startAnimation(rotation)
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var videoView: PlayerView
        var videoView: VideoView
        var username: TextView
        var caption: TextView
        var musicTitle: TextView
        var likes: TextView
        var comments: TextView
        var forward: TextView
        var userImage: CircleImageView
        var musicImage: CircleImageView
        var progressBar: ProgressBar
        var checkedImage: ImageView
        var imageButton: ImageButton

        init {
            itemView.apply {
                videoView = findViewById(R.id.playerView)
                username = findViewById(R.id.username_text)
                caption = findViewById(R.id.caption_text)
                musicTitle = findViewById(R.id.music_title)
                likes = findViewById(R.id.likeText)
                comments = findViewById(R.id.commentText)
                forward = findViewById(R.id.forwardText)
                userImage = findViewById(R.id.profile_image)
                musicImage = findViewById(R.id.image_music)
                progressBar = findViewById(R.id.progressBar)
                checkedImage = findViewById(R.id.checkedImg)
                imageButton = findViewById(R.id.imageButton)
            }
        }
    }

}