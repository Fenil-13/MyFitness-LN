package com.myfitness.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.myfitness.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra(KEY_NAME)){
            binding.tvUserName.text=intent.getStringExtra(KEY_NAME)
        }
        if(intent.hasExtra(KEY_IMAGE)){
            Glide.with(this@DetailActivity).load(intent.getStringExtra(KEY_IMAGE)).into(binding.ivProfileLargeImage)
        }
        if(intent.hasExtra(KEY_PHONE)){
            binding.tvNumber.text=intent.getStringExtra(KEY_PHONE)
        }
        if(intent.hasExtra(KEY_EMAIL)){
            binding.tvEmail.text=intent.getStringExtra(KEY_EMAIL)
        }
        setListener()

    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvNumber.setOnClickListener {
            doCall()
        }
        binding.tvEmail.setOnClickListener {
            doEmail()
        }
    }

    private fun doEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND);
        emailIntent.type = "text/plain";
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello , Good Morning");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Message from Fitness App.");


        emailIntent.type = "message/rfc822";

        try {
            startActivity(Intent.createChooser(emailIntent,
                "Send email using..."));
        } catch (ex:ActivityNotFoundException) {
            Toast.makeText(this@DetailActivity,
                "No email Apps installed in your device",
                Toast.LENGTH_SHORT).show();
        }

    }

    private fun doCall() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:" + binding.tvNumber.text )
        startActivity(callIntent)
    }
}