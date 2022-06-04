package com.myfitness.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myfitness.api.models.Location
import com.myfitness.api.models.Result
import com.myfitness.databinding.ItemFitnessBinding
import com.myfitness.listener.FitnessClickListener
import java.text.SimpleDateFormat
import java.util.*

class FitnessAdapter(private val clickListener: FitnessClickListener) : RecyclerView.Adapter<FitnessAdapter.FitnessViewHolder>() {
    private val differCallBack=object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id==newItem.id
        }


    }
    val differ = AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        return FitnessViewHolder(ItemFitnessBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
       holder.bind(differ.currentList[position],clickListener,position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    class FitnessViewHolder(val itemBookingBinding: ItemFitnessBinding): RecyclerView.ViewHolder(itemBookingBinding.root) {
       fun bind(result: Result, clickListener: FitnessClickListener, position: Int){
            with(itemBookingBinding){
                tvName.text="${result.name.title}. ${result.name.first} ${result.name.last}"
                tvBirthdate.text="${result.dob.date}"
                tvAddress.text=getLocationString(result.location)
                Glide.with(itemBookingBinding.root.context).load(result.picture.thumbnail).into(ivProfileImage)
                lytFitness.setOnClickListener {
                    clickListener.onClick(position)
                }
                tvBirthdate.text=getDate(result.dob.date)
            }

       }

        private fun getLocationString(location: Location): CharSequence? {
            val res=StringBuilder("")
            res.append(location.street.number)
            res.append(", ")
            res.append(location.street.name)
            res.append(", ")
            res.append(location.city)
            res.append(", ")
            res.append(location.state)
            res.append(", ")
            res.append(location.country)
            res.append(" - ")
            res.append(location.postcode)
            return res.toString()
        }

        private fun getDate(unFormatted: String): String {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_MONTH,unFormatted.substring(5, 7).toInt())
            cal.add(Calendar.MONTH,unFormatted.substring(8, 10).toInt())
            cal.add(Calendar.YEAR,unFormatted.substring(0, 4).toInt())
            val date=cal.time
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            return   formatter.format(date);

        }
    }

}