package com.example.moorthi.taskkttelematices.Fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moorthi.taskkttelematices.Adapter.LocationAdapter
import com.example.moorthi.taskkttelematices.Database.Realm
import com.example.moorthi.taskkttelematices.Interface.Communicator
import com.example.moorthi.taskkttelematices.Interface.OnStartDragListener
import com.example.moorthi.taskkttelematices.MainActivity
import com.example.moorthi.taskkttelematices.R
import com.example.moorthi.taskkttelematices.model.LocationModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_list.view.rv_location_list


class ListFragment : Fragment(), OnStartDragListener {
    lateinit var mContext: MainActivity
    lateinit var mList: RecyclerView
    lateinit var comm: Communicator

    lateinit var adapter: LocationAdapter
    lateinit var touchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mList = view.rv_location_list

        mContext.loadLocation()
        adapter = LocationAdapter(this,Realm.getLocations(),object: listCallback{
            override fun clickPos(location: LocationModel) {
                val fragment = MapFragment()
                val bundle = Bundle()
                bundle.putDouble("latitude", location.lat)
                bundle.putDouble("logitude",location.lng)
                fragment.setArguments(bundle)
                getFragmentManager()!!.beginTransaction().replace(R.id.objmapparent, fragment).commit()
                val tabs =
                    (activity as MainActivity?)!!.findViewById<View>(R.id.tabs) as TabLayout
                   tabs.getTabAt(0)!!.select()
            }
        })
        enableSwipeToDeleteAndUndo(view);
        mList.layoutManager = LinearLayoutManager(mContext);
        mList.adapter = adapter;
        mList.setHasFixedSize(true);
        mList.addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        var fab = view.findViewById(R.id.fab) as FloatingActionButton

        fab.setOnClickListener { view ->
            showDialog(view)
        }
        val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(adapter)

        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(view.rv_location_list)

        return view
    }

    private fun enableSwipeToDeleteAndUndo(view: View) {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(mContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeItem(position)
                val snackbar = Snackbar
                    .make(
                        view,
                        "Item was removed from the list.",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(view.rv_location_list)
    }


    private fun showDialog(view: View) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_layout)
        val lat = dialog.findViewById(R.id.objlatitude) as EditText
        val lon = dialog.findViewById(R.id.objlongtude) as EditText
        val addBtn = dialog.findViewById(R.id.objadd) as Button
        addBtn.setOnClickListener {
            if(lat.length()>0 && lon.length()>0){
                var str:String = lat.text.toString()
                var latD:Double = str.toDouble()
                var str1:String = lon.text.toString()
                var lonD:Double = str1.toDouble()
                Realm.insertLocation(latD,lonD)
                Snackbar.make(view, "Saved Successfully",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                dialog.dismiss()
}else{
     Snackbar.make(view, "Please enter value",
               Snackbar.LENGTH_LONG)
               .setAction("Action", null).show()
}
        }
        dialog.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = layoutParams
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }


}

interface listCallback{
    fun clickPos(location: LocationModel)
}
