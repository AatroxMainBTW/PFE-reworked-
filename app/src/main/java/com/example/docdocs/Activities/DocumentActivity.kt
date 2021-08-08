package com.example.docdocs.Activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View.OnLongClickListener
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.docdocs.Adapters.DocumentAdapter
import com.example.docdocs.DataBaseOp.DataBaseHandler
import com.example.docdocs.Models.Document
import com.example.docdocs.R
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList


class DocumentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var listView: ListView? = null
    private var documentList = ArrayList<Document>()
    var adapter: DocumentAdapter? = null
    var editText: EditText? = null
    var drawer: DrawerLayout? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    var navigationview: NavigationView? = null
    val db: DataBaseHandler = DataBaseHandler(this)
    val EXTRA_VALUE: String = "IdFolder"
    val EXTRA_VALUE_DOCUMENT: String = "IdDocument"
    var bundle: Bundle? = null
    var idFolder: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)

        toolbar = findViewById(R.id.toolbar)
        listView = findViewById(R.id.rv)
        editText = findViewById(R.id.ed1)
        drawer = findViewById(R.id.drawer_layout)
        navigationview = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)
        navigationview?.setNavigationItemSelectedListener(this)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_draw_open,
            R.string.navigation_draw_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()

        bundle = intent.extras!!
        idFolder = bundle?.get(EXTRA_VALUE) as Int
        documentList = db.getAllDocumentData(_idFolder = idFolder!!)
        adapter = DocumentAdapter(this, documentList)
        (listView as ListView).adapter = adapter
        listView?.isLongClickable = true;

        editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                filter(cs.toString())
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                Toast.makeText(applicationContext, "before text change", Toast.LENGTH_LONG).show()
            }

            override fun afterTextChanged(arg0: Editable) {

            }
        })

        listView?.setOnItemClickListener { parent, view, position, id -> // TODO Auto-generated method stub
            println("Document ${documentList[position]._nameDoc} with category ${documentList[position]._nameCategory}")
            val intent = Intent(this,CoordinateActivity::class.java)
            intent.putExtra(EXTRA_VALUE_DOCUMENT,documentList[position]._idDoc)
            startActivity(intent)
        }
        listView?.setOnLongClickListener(OnLongClickListener {
            val intent:Intent = Intent(this,ChoiceActivity::class.java)
            startActivity(intent)
            println("LongCLICK")
            Toast.makeText(this, "long clicked", Toast.LENGTH_SHORT).show()
            true
        })

    }

    override fun onBackPressed() {
        if (drawer?.isDrawerOpen(GravityCompat.START) == true) {
            drawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames = ArrayList<Document>()
        //looping through existing elements and adding the element to filtered list
        documentList.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it._nameDoc.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
        }
        //calling a method of the adapter class and passing the filtered list
        adapter!!.filterList(filteredNames)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_edu -> {
                documentList =
                    db.getDocumentFromCategory(_idFolder = idFolder!!, _category = "éducation")
                adapter = DocumentAdapter(this, documentList)
                (listView as ListView).adapter = adapter
            }


            R.id.nav_finance -> {
                documentList =
                    db.getDocumentFromCategory(_idFolder = idFolder!!, _category = "finance")
                adapter = DocumentAdapter(this, documentList)
                (listView as ListView).adapter = adapter
            }
            R.id.nav_admin -> {
                documentList =
                    db.getDocumentFromCategory(_idFolder = idFolder!!, _category = "administration")
                adapter = DocumentAdapter(this, documentList)
                (listView as ListView).adapter = adapter
            }
            R.id.nav_reset -> {
                documentList = db.getAllDocumentData(_idFolder = idFolder!!)
                adapter = DocumentAdapter(this, documentList)
                (listView as ListView).adapter = adapter
            }
        }
        return true
    }
}