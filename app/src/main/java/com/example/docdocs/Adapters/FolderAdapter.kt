package com.example.docdocs.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.docdocs.Activities.ChoiceActivity
import com.example.docdocs.Activities.DocumentActivity
import com.example.docdocs.Models.Folder
import com.example.docdocs.R

class FolderAdapter(private val activity: Activity, folderList: List<Folder>): BaseAdapter()  {
    private var folderList:ArrayList<Folder>? = null
    init {
        this.folderList = folderList as ArrayList
    }
    override fun getCount(): Int {
        return folderList!!.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun filterList(filteredNames: ArrayList<Folder>) {
        this.folderList = filteredNames
        notifyDataSetChanged()
    }
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val folder = this.folderList?.get(p0)
        var vi: View? = p1
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.folder_adapter,null)
        val folderName:TextView = vi.findViewById(R.id.folderName)
        val folderCat:TextView = vi.findViewById(R.id.folderCat)

        folderName.text = folderList!![p0]._nameFolder
        folderCat.text = folderList!![p0]._nameCategory




        return vi
    }
}