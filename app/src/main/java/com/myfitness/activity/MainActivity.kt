package com.myfitness.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myfitness.Repository.FitnessRepository
import com.myfitness.adapter.FitnessAdapter
import com.myfitness.databinding.ActivityMainBinding
import com.myfitness.listener.FitnessClickListener
import com.myfitness.utils.Resource
import com.myfitness.viewmodels.FitnessViewModel
import com.myfitness.viewmodels.FitnessViewModelFactory
const val KEY_NAME="com.fitness.name"
const val KEY_IMAGE="com.fitness.image"
const val KEY_PHONE="com.fitness.phone"
const val KEY_EMAIL="com.fitness.email"

class MainActivity : AppCompatActivity() {
    lateinit var fitnessAdapter: FitnessAdapter
    lateinit var binding: ActivityMainBinding
    lateinit var fitnessViewModel: FitnessViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvBooking.visibility=View.GONE
        binding.rvShimmerView.visibility=View.VISIBLE
        binding.rvShimmerView.startShimmer()
        setUpRecycleView()
        val factory=FitnessViewModelFactory(application, FitnessRepository())
        fitnessViewModel= ViewModelProvider(this@MainActivity,factory)[FitnessViewModel::class.java]
        setObserver()
    }

    private fun setObserver() {
        fitnessViewModel.allRandomUser.observe(this@MainActivity){
            response->
            if (response is Resource.Success) {
                fitnessAdapter.differ.submitList(response.data!!.results)
                binding.rvShimmerView.visibility=View.GONE
                binding.rvShimmerView.stopShimmer()
                binding.rvBooking.visibility=View.VISIBLE
            } else if (response is Resource.Error) {
                Toast.makeText(this@MainActivity, "Error : ${response.message}", Toast.LENGTH_SHORT).show()
                binding.rvBooking.visibility=View.GONE
            } else if (response is Resource.Loading) {
                binding.rvBooking.visibility=View.GONE
                binding.rvShimmerView.visibility=View.VISIBLE
                binding.rvShimmerView.startShimmer()

            }
        }
    }

    private fun setUpRecycleView() {
        fitnessAdapter=FitnessAdapter(object :FitnessClickListener{
            override fun onClick(position: Int) {
                val intent=Intent(this@MainActivity,DetailActivity::class.java)
                val currentResult=fitnessAdapter.differ.currentList[position]
                intent.putExtra(KEY_NAME,"${currentResult.name.title}. ${currentResult.name.first} ${currentResult.name.last}")
                intent.putExtra(KEY_IMAGE,currentResult.picture.large)
                intent.putExtra(KEY_PHONE,currentResult.phone)
                intent.putExtra(KEY_EMAIL,currentResult.email)
                startActivity(intent)
            }

        })
        binding.rvBooking.apply {
            layoutManager=LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL,false)
            setHasFixedSize(true)
            adapter=fitnessAdapter
        }
    }
}