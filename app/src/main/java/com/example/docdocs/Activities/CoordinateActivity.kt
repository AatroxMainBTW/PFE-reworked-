package com.example.docdocs.Activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.docdocs.Adapters.CoordinateAdapter
import com.example.docdocs.DataBaseOp.DataBaseHandler
import com.example.docdocs.Fragments.DocumentCoordinateFragment
import com.example.docdocs.Fragments.FolderCoordinateFragment
import com.example.docdocs.Models.Coordinate
import com.example.docdocs.R
import com.example.docdocs.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlin.collections.ArrayList

class CoordinateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    var listView: ListView? = null
    private var coordinateList = ArrayList<Coordinate>()
    var adapter: CoordinateAdapter? = null
    var editText: EditText? = null
    var drawer: DrawerLayout? = null
    var toolbar: androidx.appcompat.widget.Toolbar? = null
    var navigationview: NavigationView? = null
    val db: DataBaseHandler = DataBaseHandler(this)
    val EXTRA_VALUE_DOCUMENT: String = "IdDocument"
    val EXTRA_VALUE_POSX: String = "posX"
    val EXTRA_VALUE_POSY: String = "posY"
    val EXTRA_VALUE_NAMEPOS: String = "namePos"
    val EXTRA_VALUE: String = "IdFolder"
    var bundle: Bundle? = null
    var bundle2: Bundle? = null
    var identifier: Int? = null
    var idFolder: Int? = null
    var obj:FolderCoordinateFragment? = null
    lateinit var biding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_coordinate)
        toolbar = findViewById(R.id.toolbar)
        /* listView = findViewById(R.id.lv)
       editText = findViewById(R.id.ed2)*/
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        navigationview = findViewById(R.id.nav_view)
        navigationview?.setNavigationItemSelectedListener(this)
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_draw_open,
            R.string.navigation_draw_close
        )
        drawer?.addDrawerListener(toggle)
        toggle.syncState()
        bundle = intent.extras!!
        
        if(bundle!!.keySet().contains(EXTRA_VALUE)){
            Log.v("idFolder","poop")
            identifier = bundle?.get(EXTRA_VALUE) as Int
            replaceFragment(FolderCoordinateFragment())
        } else{
            Log.v("idDocs","pipi")
            identifier = bundle?.get(EXTRA_VALUE_DOCUMENT) as Int
            replaceFragment(DocumentCoordinateFragment())
        }



        /* bundle2?.putString("MyData",idDocs.toString())
         obj?.arguments = bundle2
         coordinateList = db.getAllDocumentCoordinate(idDocs!!)
         adapter = CoordinateAdapter(this, coordinateList)
         (listView as ListView).adapter = adapter
         listView?.isLongClickable = true;*/

/* editText?.addTextChangedListener(object : TextWatcher {
    override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
        filter(cs.toString())
    }

    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
        Toast.makeText(applicationContext, "before text change", Toast.LENGTH_LONG).show()
    }

    override fun afterTextChanged(arg0: Editable) {

    }
})*/


/* listView?.setOnItemClickListener { parent, view, position, id -> // TODO Auto-generated method stub
    val intent = Intent(this,MapsActivity::class.java)
    intent.putExtra(EXTRA_VALUE_POSX,coordinateList[position]._posX)
    intent.putExtra(EXTRA_VALUE_POSY,coordinateList[position]._posY)
    intent.putExtra(EXTRA_VALUE_NAMEPOS,coordinateList[position]._nameCoord)
    startActivity(intent)
}*/
}


override fun onBackPressed() {
if (drawer?.isDrawerOpen(GravityCompat.START) == true) {
    drawer?.closeDrawer(GravityCompat.START)
} else {
    super.onBackPressed()
}

}

/* private fun filter(text: String) {
//new array list that will hold the filtered data
val filteredNames = ArrayList<Coordinate>()
//looping through existing elements and adding the element to filtered list
coordinateList.filterTo(filteredNames) {
    //if the existing elements contains the search input
    it._nameCoord.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
}
//calling a method of the adapter class and passing the filtered list
adapter!!.filterList(filteredNames)
}*/

override fun onNavigationItemSelected(item: MenuItem): Boolean {
when (item.itemId) {
    R.id.nav_edu -> {
        replaceFragment(FolderCoordinateFragment())
    }


    R.id.nav_finance -> {
        replaceFragment(DocumentCoordinateFragment())
    }
    R.id.nav_admin -> {

    }
    R.id.nav_reset -> {

    }
}
return true
}


private fun replaceFragment(fragment:Fragment){
val fragmentManager = supportFragmentManager
val fragmentTransaction = fragmentManager.beginTransaction()
fragmentTransaction.replace(R.id.fr1,fragment)
fragmentTransaction.commit()
}

fun getData(): String? {
return identifier.toString()
}

}