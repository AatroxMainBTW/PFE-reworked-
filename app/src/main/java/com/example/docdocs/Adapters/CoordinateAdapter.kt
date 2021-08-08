package com.example.docdocs.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.docdocs.Models.Coordinate
import com.example.docdocs.R

class CoordinateAdapter(private val activity: Activity, coordinateList: List<Coordinate>): BaseAdapter()  {
    private var coordinateList = ArrayList<Coordinate>()
    init {
        this.coordinateList = coordinateList as ArrayList
    }
    override fun getCount(): Int {
        return coordinateList!!.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun filterList(filteredNames: ArrayList<Coordinate>) {
        this.coordinateList = filteredNames
        notifyDataSetChanged()
    }
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val coordinate = this.coordinateList?.get(p0)
        var vi: View? = p1
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.coordinate_adapter,null)
        val coordinateName: TextView = vi.findViewById(R.id.coordName)
        val coordinateLat: TextView = vi.findViewById(R.id.coordLatitude)
        val coordinateLong: TextView = vi.findViewById(R.id.coordLongitude)

        coordinateName.text = coordinateList[p0]._nameCoord
        coordinateLat.text = coordinateList[p0]._posX
        coordinateLong.text = coordinateList[p0]._posY



        return vi
    }
}