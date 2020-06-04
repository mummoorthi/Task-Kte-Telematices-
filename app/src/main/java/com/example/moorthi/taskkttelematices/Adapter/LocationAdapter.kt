package com.example.moorthi.taskkttelematices.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moorthi.taskkttelematices.model.Controller
import com.example.moorthi.taskkttelematices.Fragment.ItemMoveCallbackListener
import com.example.moorthi.taskkttelematices.Fragment.listCallback
import com.example.moorthi.taskkttelematices.Interface.OnStartDragListener
import com.example.moorthi.taskkttelematices.R
import com.example.moorthi.taskkttelematices.model.LocationModel
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.row.view.*
import java.util.*

class LocationAdapter (private val startDragListener: OnStartDragListener, var locations: OrderedRealmCollection<LocationModel>?, var callback : listCallback) : RealmRecyclerViewAdapter<LocationModel, LocationAdapter.MyViewHolder>(locations, true),
    ItemMoveCallbackListener.Listener {
    private var latlngUsers = emptyList<LocationModel>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mValue: TextView = view.tv_location
        val objParent: RelativeLayout = view.objParent
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = locations?.get(position)
        if (location != null) {
            holder.mValue.text = "${location.lat},${location.lng}"
            holder.mValue.setOnClickListener(){
                callback.clickPos(location)
            }
            holder.objParent.setOnClickListener(){
                callback.clickPos(location)
            }
            holder.objParent.setOnLongClickListener{
             //   if (event.action == MotionEvent.ACTION_DOWN) {
                    this.startDragListener.onStartDrag(holder)
              //  }
                return@setOnLongClickListener true
            }
            holder.mValue.setOnLongClickListener{
                //   if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
                //  }
                return@setOnLongClickListener true
            }
         /*   holder.mValue.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    this.startDragListener.onStartDrag(holder)
                }
                return@setOnTouchListener true
            }*/
        }

    }
    fun removeItem(position: Int) {
        val location = locations?.get(position)
        //locations?.removeAt(position)
        Controller.delete(location!!.id)
        notifyItemRemoved(position)
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        locations?.let { latlngUsers.addAll(it) }
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(latlngUsers, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(latlngUsers, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)    }

    override fun onRowSelected(itemViewHolder: MyViewHolder) {
    }

    override fun onRowClear(itemViewHolder: MyViewHolder) {
    }

}