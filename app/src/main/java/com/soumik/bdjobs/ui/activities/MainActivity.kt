package com.soumik.bdjobs.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.soumik.bdjobs.R
import com.soumik.bdjobs.ui.JobInfoViewModel
import com.soumik.bdjobs.ui.adapters.JobListAdapter
import com.soumik.bdjobs.utils.Status
import com.soumik.bdjobs.utils.lightStatusBar
import com.soumik.bdjobs.utils.toolbarStyle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "HOME"
    }

    private lateinit var mViewModel: JobInfoViewModel
    private lateinit var mAdapter:JobListAdapter

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mProgressBar:ProgressBar
    private lateinit var mParentView:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lightStatusBar(this,true)
        setContentView(R.layout.activity_main)

        init()

        toolbarStyle(this,mToolbar,getString(R.string.title_home))
    }

    override fun onStart() {
        super.onStart()

        setUpRecyclerView()

        setUpObserver()

    }

    private fun setUpObserver() {
        Log.d(TAG, "setUpObserver: triggered")
        lifecycleScope.launch {
            mViewModel.getJobList().observe(this@MainActivity, Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        mProgressBar.visibility= View.GONE
                        mRecyclerView.visibility=View.VISIBLE

                        mAdapter.differ.submitList(it.data?.data)
                    }
                    Status.ERROR-> {
                        mProgressBar.visibility= View.GONE
                        mRecyclerView.visibility=View.GONE

                        Snackbar.make(mParentView,it.error!!,Snackbar.LENGTH_INDEFINITE).apply {
                            setAction("Ok") { this.dismiss() }
                        }.show()
                    }
                    Status.LOADING->{
                        mProgressBar.visibility= View.VISIBLE
                        mRecyclerView.visibility=View.GONE
                    }
                }
            })
        }

    }

    private fun setUpRecyclerView() {
        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

    }

    private fun init() {
        mViewModel = ViewModelProvider(this).get(JobInfoViewModel::class.java)
        mAdapter = JobListAdapter()
        mToolbar = findViewById(R.id.tb_home)
        mRecyclerView =findViewById(R.id.rv_job_item)
        mProgressBar = findViewById(R.id.pb_home)
        mParentView = findViewById(R.id.cl_parent)
    }
}