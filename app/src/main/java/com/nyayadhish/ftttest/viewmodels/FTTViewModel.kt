package com.nyayadhish.ftttest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyayadhish.ftttest.R
import com.nyayadhish.ftttest.model.UserCardInfo

class FTTViewModel: ViewModel(){
     val testDataLive = MutableLiveData<List<UserCardInfo>>()

    fun getListData() : MutableLiveData<List<UserCardInfo>>{
        Log.i("ViewModel","In View model")
        var mList: ArrayList<UserCardInfo> = arrayListOf()
        mList.add(UserCardInfo("Bruce Wayne","batman@jl.com", R.drawable.user_1,"0","30",""))
        mList.add(UserCardInfo("Barry Allen","barry@jl.com",R.drawable.user_1,"10","100",""))
        mList.add(UserCardInfo("Clerk Kent","superman@jl.com",R.drawable.user_1,"20","30",""))
        mList.add(UserCardInfo("Tony Stark","ironman@marvel.com",R.drawable.user_1,"40","100",""))
        mList.add(UserCardInfo("Bruce Banner","hulk@marvel.com",R.drawable.user_1,"0","30",""))
        mList.add(UserCardInfo("Peter Parker","spiderman@marvel.com",R.drawable.user_1,"20","100",""))

        testDataLive.value = mList
        return testDataLive
    }


}