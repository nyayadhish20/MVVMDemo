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

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(mList.isNotEmpty()){
            holder.setIsRecyclable(false)
            holder.itemView.tv_name.text = mList[position].name
            holder.itemView.tv_email.text = mList[position].email
            holder.itemView.start_time.text = String.format("%s %s","Start Time:",mList[position].start_time)
            holder.itemView.end_time.text = String.format("%s %s","End Time:",mList[position].end_time)
            holder.itemView.iv_user_image.setImageDrawable(mContext.getDrawable(mList[position].profileImage?:0))
            holder.itemView.counter.text = mList[position].time



            holder.handler.postDelayed({
                holder.timer = object: CountDownTimer(((mList[position].end_time?.toLong()?.minus(mList[position].start_time?.toLong()?:0))?:0)*1000,1000){
                    override fun onFinish() {
                        Log.i("List Size = ",mList.size.toString())
                        Log.i("List position = ",position.toString())
                        if(position < mList.size) {
                            mList.remove(mList[position])
                            notifyItemRemoved(position)
                            //notifyDataSetChanged()
                            //notifyItemRangeChanged(position,itemCount.minus(position))
                        }
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        holder.updateText((millisUntilFinished.div(1000)).toString())

                    }
                }
                (holder.timer as CountDownTimer).start()
            },(mList[position].start_time?.toLong()?:0)*1000)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun updateText(time: String){
            counter.text  = time
        }

        var counter: TextView = view.findViewById(R.id.counter)
        val handler = Handler()
        var timer: CountDownTimer? = null
    }
}

