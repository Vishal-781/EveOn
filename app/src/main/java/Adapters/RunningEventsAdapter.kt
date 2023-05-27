package Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eveon.R
import com.example.eveon.activitiesandfragments.CardViewFully
import models.Event



class RunningEventsAdapter(
    mContext:Context,
    mEventList:List<Event>
 ):RecyclerView.Adapter<RunningEventsAdapter.ViewHolder?>() {
    private val mContext: Context
    private val mEventList: List<Event>

    init {
        this.mContext = mContext
        this.mEventList = mEventList

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var touchid: View? = null
        var eventimage: ImageView? = null
        var eventname: TextView? = null
        var eventlocation: TextView? = null
        var eventdatetime: TextView? = null

        init {
            touchid = view.findViewById(R.id.touchId)
            eventimage = view.findViewById(R.id.imageEvent)
            eventname = view.findViewById(R.id.EventName)
            eventlocation = view.findViewById(R.id.EventLocation)
            eventdatetime = view.findViewById(R.id.EventDateTime)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mEventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val event = mEventList[position]
//     Glide.with(mcontext).load(event.image).placeholder(R.drawable.signupname).into(holder.eventimage)
        holder.eventname?.text = event.eName
        holder.eventlocation!!.text = event.eLoc
        holder.eventdatetime!!.text =
            "${event.eDay} ${event.eMonth} ${event.eYear} ${event.eHour}:${event.eMinute}"

        holder.touchid!!.setOnClickListener {
            val intent = Intent(mContext, CardViewFully::class.java)
            intent.putExtra("Url_event_id", event.eid)
            mContext.startActivity(intent)

        }
    }
}