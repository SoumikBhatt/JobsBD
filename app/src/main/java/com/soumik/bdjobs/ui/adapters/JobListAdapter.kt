package com.soumik.bdjobs.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soumik.bdjobs.BDJobs
import com.soumik.bdjobs.R
import com.soumik.bdjobs.data.Data
import com.soumik.bdjobs.utils.addOrdinals
import java.text.SimpleDateFormat


//
// Created by Soumik on 12/25/2020.
// Copyright (c) 2020 Soumik.  All rights reserved.
//
class JobListAdapter:RecyclerView.Adapter<JobListAdapter.Holder>() {

    val differ = AsyncListDiffer(this,DiffUtilItemCallback<Data>())
    private var onItemClicked:((Data)->Unit)?=null


    inner class Holder(itemView:View):RecyclerView.ViewHolder(itemView) {

        private val parentView:RelativeLayout = itemView.findViewById(R.id.rl_parent)
        private val featuredView:LinearLayout = itemView.findViewById(R.id.ll_featured)
        private val positionTV:TextView = itemView.findViewById(R.id.tv_position)
        private val companyTV:TextView = itemView.findViewById(R.id.tv_company_name)
        private val deadLineTV:TextView = itemView.findViewById(R.id.tv_deadline)
        private val experienceTV:TextView = itemView.findViewById(R.id.tv_experience)
        private val salaryTV:TextView = itemView.findViewById(R.id.tv_salary)
        private val logoIV:ImageView = itemView.findViewById(R.id.iv_company_logo)

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bindData(data:Data?) {

            if (data?.isFeatured!!) {
                parentView.setBackgroundResource(R.drawable.bg_featured)
                featuredView.visibility=View.VISIBLE
            }

            Glide.with(BDJobs.mContext)
                .load(data.logo)
                .into(logoIV)

            positionTV.text = data.jobTitle
            if (!data.recruitingCompanysProfile.isNullOrEmpty()) companyTV.text = data.recruitingCompanysProfile

            val parser = SimpleDateFormat("M/dd/yyyy")
            val day = SimpleDateFormat("dd").format(parser.parse(data.deadline!!)!!)
            val month = SimpleDateFormat("MMM").format(parser.parse(data.deadline)!!)
            val year = SimpleDateFormat("yyyy").format(parser.parse(data.deadline)!!)

            deadLineTV.text =  "${day.addOrdinals()} $month, $year"

            experienceTV.text = if (data.maxExperience!=null && data.minExperience!=null) "${data.minExperience} to ${data.maxExperience} year(s)"
            else if (data.maxExperience!=null && data.minExperience==null) "Up to ${data.maxExperience} year(s)"
            else if (data.maxExperience==null && data.minExperience!=null) "At least ${data.minExperience} year(s)"
            else "N/A"

            salaryTV.text = if (!data.minSalary.isNullOrEmpty() && !data.maxSalary.isNullOrEmpty()) "Tk. ${data.minSalary} - ${data.maxSalary} (Monthly)"
            else if (!data.minSalary.isNullOrEmpty() && data.maxSalary.isNullOrEmpty()) "Tk. ${data.minSalary} (Monthly)"
            else if (data.minSalary.isNullOrEmpty() && !data.maxSalary.isNullOrEmpty()) "Up to Tk. ${data.maxSalary} (Monthly)"
            else "Negotiable"

            itemView.setOnClickListener {
                onItemClicked?.let { it(data) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_jobs,parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = differ.currentList[position]

        holder.bindData(data)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun onItemClicked(listener:(Data)->Unit) {
        onItemClicked = listener
    }
}