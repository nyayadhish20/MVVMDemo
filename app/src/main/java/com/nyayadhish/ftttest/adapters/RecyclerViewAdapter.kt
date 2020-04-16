package com.nyayadhish.ftttest.adapters

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyayadhish.ftttest.R
import com.nyayadhish.ftttest.model.UserCardInfo
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class RecyclerViewAdapter(private val mList: ArrayList<UserCardInfo>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    private val lstHolder: List<ViewHolder> = arrayListOf()

    init {
        //startUpdateTime()
    }

    private fun startUpdateTime() {
        val t:Timer = Timer()
        t.schedule(object: TimerTask(){
            override fun run() {
                handler.post(runnable)
            }
        },1000,1000)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    val handler = Handler()
    private val runnable: Runnable = object : Runnable{
        override fun run() {
            synchronized(lstHolder){
                for (holder in lstHolder){
                    holder.updateTime()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(mList.isNotEmpty()){
            holder.itemView.tv_name.text = mList[position].name
            holder.itemView.tv_email.text = mList[position].email
            holder.itemView.start_time.text = String.format("%s %s","Start Time:",mList[position].start_time)
            holder.itemView.end_time.text = String.format("%s %s","End Time:",mList[position].end_time)
            holder.itemView.iv_user_image.setImageDrawable(mContext.getDrawable(mList[position].profileImage?:0))
            holder.itemView.counter.text = mList[position].time
            //holder.setData(mList[position])

            Handler().postDelayed({
                holder.timer = object: CountDownTimer(((mList[position].end_time?.toLong()?.minus(mList[position].start_time?.toLong()?:0))?:0)*1000,1000){
                    override fun onFinish() {
                        mList.removeAt(position)
                        Log.i("List Size = ",mList.size.toString())
                        Log.i("List position = ",position.toString())
                        notifyItemRemoved(position)
                        //notifyDataSetChanged()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        holder.counter.text = (millisUntilFinished.div(1000)).toString()
                    }
                }
                (holder.timer as CountDownTimer).start()
            },mList[position].start_time?.toLong()?:0)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var counter: TextView = view.findViewById(R.id.counter)

        fun setData(cardInfo: UserCardInfo){
            counter.text = cardInfo.time

        }

        fun updateTime() {

        }

        var timer: CountDownTimer? = null


    }
}

