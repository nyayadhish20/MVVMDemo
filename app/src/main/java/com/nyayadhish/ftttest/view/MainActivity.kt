package com.nyayadhish.ftttest.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyayadhish.ftttest.R
import com.nyayadhish.ftttest.adapters.RecyclerViewAdapter
import com.nyayadhish.ftttest.model.UserCardInfo
import com.nyayadhish.ftttest.viewmodels.FTTViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FTTViewModel
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

    /**
     * Observe the events of live data
     */
    private fun setObserver() {
        viewModel.getListData().observe(this, Observer {
            when (it) {
                is ArrayList<UserCardInfo> -> inflateUI(it)
            }
        })
    }

    /**
     * Inflate UI dynamic as per list
     */
    private fun inflateUI(mList: ArrayList<UserCardInfo>) {
        for (position in 0 until mList.size) {
            val view = LayoutInflater.from(this).inflate(
                R.layout.list_item,
                ll_main,
                false
            )

            view.tv_name.text = mList[position].name
            view.tv_email.text = mList[position].email
            view.start_time.text =
                String.format("%s %s", "Start Time:", mList[position].start_time)
            view.end_time.text =
                String.format("%s %s", "End Time:", mList[position].end_time)
            view.iv_user_image.setImageDrawable(
                getDrawable(
                    mList[position].profileImage ?: 0
                )
            )
            view.counter.text = mList[position].time

            Handler().postDelayed({
                val timer = object : CountDownTimer(
                    ((mList[position].current_time?.toLong()?.minus(
                        mList[position].start_time?.toLong() ?: 0
                    )) ?: 0) * 1000, 1000
                ) {
                    override fun onFinish() {
                        Log.i("List Size = ", mList.size.toString())
                        Log.i("List position = ", position.toString())
                        ll_card_View.removeView(view)
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        view.counter.text = (millisUntilFinished.div(1000)).toString()
                    }
                }
                (timer as CountDownTimer).start()
            }, (mList[position].start_time?.toLong() ?: 0) * 1000)

            ll_card_View.addView(view)
        }
    }
    /**
     * Tried with Another approach as well
     */

    private fun setRecyclerViewAdapter(mList: java.util.ArrayList<UserCardInfo>) {
        rv_user_card.layoutManager = LinearLayoutManager(this)
        mAdapter = RecyclerViewAdapter(mList)
        rv_user_card.adapter = mAdapter
    }

}
