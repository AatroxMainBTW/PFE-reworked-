package com.example.docdocs.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.docdocs.Adapters.FolderAdapter
import com.example.docdocs.DataBaseOp.DataBaseHandler
import com.example.docdocs.Models.Folder
import com.example.docdocs.R
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList


class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var gridView: GridView? = null
    private var folderList = ArrayList<Folder>()
    var adapter: FolderAdapter? = null
    var editText: EditText? = null
    var drawer: DrawerLayout? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    var navigationview: NavigationView? = null
    private val db: DataBaseHandler = DataBaseHandler(this)
    private val EXTRA_VALUE: String = "IdFolder"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        gridView = findViewById(R.id.gl)
        editText = findViewById(R.id.ed)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_create_24)
        alertDialogBuilder.setMessage("Choose Folder cooordinate or document that folder contain")
        alertDialogBuilder.setTitle("Choose")


        //SideNav drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        navigationview = findViewById(R.id.nav_view)
        navigationview?.setNavigationItemSelectedListener(this)
        var toogle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_draw_open,
            R.string.navigation_draw_close
        )
        drawer?.addDrawerListener(toogle)
        toogle.syncState()




        folderList = db.getFolderData()
        adapter = FolderAdapter(this, folderList)
        (gridView as GridView).adapter = adapter

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

        gridView?.setOnItemClickListener { parent, view, position, id -> // TODO Auto-generated method stub

            alertDialogBuilder.setPositiveButton(
                "Documents",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(this,DocumentActivity::class.java)
                    intent.putExtra(EXTRA_VALUE,folderList[position]._idFolder)
                    startActivity(intent)
                }).setNegativeButton(
                "Folder Coordinate",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(this,CoordinateActivity::class.java)
                    intent.putExtra(EXTRA_VALUE,folderList[position]._idFolder)
                    startActivity(intent)
                }).setNeutralButton("Finish",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    println("Finished")
                })
            val alertd:AlertDialog = alertDialogBuilder.create()
            alertd.show()


        }

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
        val filteredNames = ArrayList<Folder>()
        //looping through existing elements and adding the element to filtered list
        folderList.filterTo(filteredNames) {
            //if the existing elements contains the search input
            it._nameFolder.lowercase(Locale.getDefault())
                .contains(text.lowercase(Locale.getDefault()))
        }
        //calling a method of the adapter class and passing the filtered list
        adapter!!.filterList(filteredNames)
    }

    /*  private fun filterCategory(text: String) {
          //new array list that will hold the filtered data
          var filteredNames = ArrayList<Folder>()
          //looping through existing elements and adding the element to filtered list
          if (text == "all"){
              filteredNames = folderList
          } else{
              folderList.filterTo(filteredNames) {
                  //if the existing elements contains the search input
                  it._nameCategory.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
              }
          }

          //calling a method of the adapter class and passing the filtered list
          adapter!!.filterList(filteredNames)
      } */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_edu -> {
                folderList = db.getFolderFromCategory(_category = "éducation")
                adapter = FolderAdapter(this, folderList)
                (gridView as GridView).adapter = adapter
            }


            R.id.nav_finance -> {
                folderList = db.getFolderFromCategory(_category = "finance")
                adapter = FolderAdapter(this, folderList)
                (gridView as GridView).adapter = adapter
            }
            R.id.nav_admin -> {
                folderList = db.getFolderFromCategory(_category = "administration")
                adapter = FolderAdapter(this, folderList)
                (gridView as GridView).adapter = adapter
            }
            R.id.nav_reset -> {
                folderList = db.getFolderData()
                adapter = FolderAdapter(this, folderList)
                (gridView as GridView).adapter = adapter
            }
        }
        return true
    }

}