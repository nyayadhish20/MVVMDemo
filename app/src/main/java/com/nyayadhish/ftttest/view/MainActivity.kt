package com.nyayadhish.ftttest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyayadhish.ftttest.R
import com.nyayadhish.ftttest.adapters.RecyclerViewAdapter
import com.nyayadhish.ftttest.model.UserCardInfo
import com.nyayadhish.ftttest.viewmodels.FTTViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FTTViewModel
    //private var mList : ArrayList<UserCardInfo> = arrayListOf()
    private lateinit var mAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        obtainViewModels()
        setObserver()
    }

    private fun obtainViewModels() {
        viewModel = ViewModelProvider(this).get(FTTViewModel::class.java)
    }

    private fun setObserver() {
        viewModel.getListData().observe(this, Observer {
            when(it){
                is ArrayList<UserCardInfo> -> setRecyclerViewAdapter(it)
            }
        })
    }

    private fun setRecyclerViewAdapter(mList: java.util.ArrayList<UserCardInfo>) {
        rv_user_card.layoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerViewAdapter(mList)
        rv_user_card.adapter = mAdapter
    }


}
