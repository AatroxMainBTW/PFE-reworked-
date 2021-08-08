package com.example.docdocs.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.docdocs.R
import com.example.docdocs.Models.Document


class DocumentAdapter(private val activity: Activity, documentList: List<Document>): BaseAdapter()  {
    private var documentList = ArrayList<Document>()
    init {
        this.documentList = documentList as ArrayList
    }
    override fun getCount(): Int {
        return documentList!!.size
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    fun filterList(filteredNames: ArrayList<Document>) {
        this.documentList = filteredNames
        notifyDataSetChanged()
    }
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val document = this.documentList?.get(p0)
        var vi: View? = p1
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.document_adapter,null)
        val documentName: TextView = vi.findViewById(R.id.docsName)
        val documentCat: TextView = vi.findViewById(R.id.docsCat)

        documentName.text = documentList[p0]._nameDoc
        documentCat.text = documentList[p0]._nameCategory



        return vi
    }
}