package Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eveon.R
import models.Event



class RunningEventsAdapter(
     mcontext:Context,
     meventlist:List<Event>
 ):RecyclerView.Adapter<RunningEventsAdapter.ViewHolder?>()
{
 private val mcontext:Context
 private val meventlist:List<Event>

    init {
        this.mcontext= mcontext
        this.meventlist=meventlist

    }

    inner class ViewHolder(viewitem: View):RecyclerView.ViewHolder(viewitem)
    {











    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view:View=LayoutInflater.from(mcontext).inflate(R.layout.card_view,parent,false)
       return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return  meventlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
