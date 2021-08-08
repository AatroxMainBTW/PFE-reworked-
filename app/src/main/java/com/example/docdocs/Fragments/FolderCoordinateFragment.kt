package com.example.docdocs.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.docdocs.Activities.CoordinateActivity
import com.example.docdocs.Activities.MapsActivity
import com.example.docdocs.Adapters.CoordinateAdapter
import com.example.docdocs.DataBaseOp.DataBaseHandler
import com.example.docdocs.Models.Coordinate
import com.example.docdocs.R
import java.util.*
import kotlin.collections.ArrayList



class FolderCoordinateFragment: Fragment() {
    // TODO: Rename and change types of parameters

    var listView: ListView? = null
    private var coordinateList = ArrayList<Coordinate>()
    var adapter: CoordinateAdapter? = null
    var editText: EditText? = null
    var db: DataBaseHandler = DataBaseHandler(null)
    val EXTRA_VALUE_POSX: String = "posX"
    val EXTRA_VALUE_POSY: String = "posY"
    val EXTRA_VALUE_NAMEPOS: String = "namePos"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView:View = inflater.inflate(R.layout.fragment_coordinate_folder, container, false)
        listView = rootView.findViewById(R.id.lvFolder)
        editText = rootView.findViewById(R.id.edFolder)

        return rootView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState)
        val activity: CoordinateActivity? = activity as CoordinateActivity?
        val myDataFromActivity: String? = activity!!.getData()
        Log.v("GGWP","ACTIVITY CREATED")
        Log.v("param1","$myDataFromActivity")
        db = DataBaseHandler(activity)
        coordinateList =   db.getAllFolderCoordinate(myDataFromActivity!!.toInt())
        adapter = CoordinateAdapter(activity, coordinateList)
        (listView as ListView).adapter = adapter
        listView?.isLongClickable = true;


listView?.setOnItemClickListener { parent, view, position, id -> // TODO Auto-generated method stub
    val intent = Intent(activity, MapsActivity::class.java)
    intent.putExtra(EXTRA_VALUE_POSX,coordinateList[position]._posX)
    intent.putExtra(EXTRA_VALUE_POSY,coordinateList[position]._posY)
    intent.putExtra(EXTRA_VALUE_NAMEPOS,coordinateList[position]._nameCoord)
    startActivity(intent)
}

        editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                filter(cs.toString())
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

            }

            override fun afterTextChanged(arg0: Editable) {

            }
        })
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<Coordinate>()
        //looping through existing elements and adding the element to filtered list
        coordinateList.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it._nameCoord.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
        }
        //calling a method of the adapter class and passing the filtered list
        adapter!!.filterList(filteredNames)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}