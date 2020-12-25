package com.soumik.bdjobs.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.soumik.bdjobs.R
import com.soumik.bdjobs.data.Data
import com.soumik.bdjobs.utils.addOrdinals
import com.soumik.bdjobs.utils.lightStatusBar
import com.soumik.bdjobs.utils.toolbarStyle
import java.text.SimpleDateFormat

class DetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "JOB_DETAILS"
        const val KEY_JOB_DATA = "JOB_DATA"
    }

    private var mData:Data?=null
    private lateinit var mToolbar:Toolbar
    private lateinit var mScrollBar:ScrollView
    private lateinit var mLogoView:ImageView
    private lateinit var mSalaryTV:TextView
    private lateinit var mFeaturedView:LinearLayout
    private lateinit var mPositionTV:TextView
    private lateinit var mCompanyTV:TextView
    private lateinit var mDeadlineTV:TextView
    private lateinit var mExperienceTV:TextView
    private lateinit var mApplicationTV:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lightStatusBar(this,true)
        setContentView(R.layout.activity_details)

        init()

        toolbarStyle(this,mToolbar,getString(R.string.title_details),true)
    }

    override fun onStart() {
        super.onStart()

        setUpUI()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun setUpUI() {

        if (!this.isFinishing) Glide.with(this).load(mData?.logo).into(mLogoView)
        if (mData?.isFeatured!!) mFeaturedView.visibility=View.VISIBLE else mFeaturedView.visibility=View.GONE

        mPositionTV.text = mData?.jobTitle
        mCompanyTV.text = mData?.recruitingCompanysProfile

        val parser = SimpleDateFormat("M/dd/yyyy")
        val day = SimpleDateFormat("dd").format(parser.parse(mData?.deadline!!)!!)
        val month = SimpleDateFormat("MMM").format(parser.parse(mData?.deadline!!)!!)
        val year = SimpleDateFormat("yyyy").format(parser.parse(mData?.deadline!!)!!)

        mDeadlineTV.text =  "Apply Before ${day.addOrdinals()} $month, $year"

        mExperienceTV.text = if (mData?.maxExperience!=null && mData?.minExperience!=null) "${mData?.minExperience} to ${mData?.maxExperience} year(s)"
        else if (mData?.maxExperience!=null && mData?.minExperience==null) "Up to ${mData?.maxExperience} year(s)"
        else if (mData?.maxExperience==null && mData?.minExperience!=null) "At least ${mData?.minExperience} year(s)"
        else "N/A"

        mSalaryTV.text = if (!mData?.minSalary.isNullOrEmpty() && !mData?.maxSalary.isNullOrEmpty()) "Tk. ${mData?.minSalary} - ${mData?.maxSalary} (Monthly)"
        else if (!mData?.minSalary.isNullOrEmpty() && mData?.maxSalary.isNullOrEmpty()) "Tk. ${mData?.minSalary} (Monthly)"
        else if (mData?.minSalary.isNullOrEmpty() && !mData?.maxSalary.isNullOrEmpty()) "Up to Tk. ${mData?.maxSalary} (Monthly)"
        else "Negotiable"

        mApplicationTV.text = Html.fromHtml(mData?.jobDetails?.applyInstruction)
    }

    private fun init() {
        mData = intent.getSerializableExtra(KEY_JOB_DATA) as Data?
        mToolbar = findViewById(R.id.tb_details)
        mScrollBar = findViewById(R.id.sv_details)
        mLogoView = findViewById(R.id.iv_logo)
        mSalaryTV = findViewById(R.id.tv_salary)
        mFeaturedView = findViewById(R.id.ll_featured)
        mPositionTV = findViewById(R.id.tv_position)
        mCompanyTV = findViewById(R.id.tv_company_name)
        mDeadlineTV = findViewById(R.id.tv_deadline)
        mExperienceTV = findViewById(R.id.tv_experience)
        mApplicationTV = findViewById(R.id.tv_apply)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        finish()
        return super.onOptionsItemSelected(item)
    }
}