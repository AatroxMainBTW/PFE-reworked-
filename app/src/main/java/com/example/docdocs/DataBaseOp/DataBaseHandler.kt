package com.example.docdocs.DataBaseOp

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.docdocs.Fragments.FolderCoordinateFragment
import com.example.docdocs.Models.Category
import com.example.docdocs.Models.Coordinate
import com.example.docdocs.Models.Document
import com.example.docdocs.Models.Folder

class DataBaseHandler(var context:Context?) : SQLiteOpenHelper(
    context, "DocDocs", null,
    1
) {


    val DATABASENAME = "DocDocs"

    val folderHaveTable = "FolderHave"
    val documentHaveTable = "DocumentHave"
    val contain = "FolderContain"

    val categoryTable = "Category"
    val COL_CATEGORYID = "Idcategory"
    val COL_CATEGORYNAME = "Namecategory"

    val folderTable = "Folder"
    val COL_FOLDERID = "Idfolder"
    val COL_FOLDERNAME = "Namefolder"

    val documentTable = "Document"
    val COL_DOCUMENTID = "Iddoc"
    val COL_DOCUMENTNAME = "Namedoc"

    val coordinateTable = "Coordinate"
    val COL_COORDINATEID = "Idcoord"
    val COL_COORDINATENAME = "Namecoord"
    val COL_POSX = "posX"
    val COL_POSY = "posY"

    override fun onCreate(db: SQLiteDatabase?) {
        /*  val createTableFolder  =  "CREATE TABLE Folder (Idfolder INTEGER PRIMARY KEY AUTOINCREMENT, Namefolder TEXT, Idcategory INTEGER REFERENCES Category)"
        db?.execSQL(createTableFolder)

      val createTableCategory = "CREATE TABLE $categoryTable ($COL_CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CATEGORYNAME TEXT)"
      val createTableFolder  =  "CREATE TABLE $folderTable ($COL_FOLDERID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_FOLDERNAME TEXT, $COL_CATEGORYID INTEGER REFERENCES $categoryTable)"
      val createTableDocument = "CREATE TABLE $documentTable ($COL_DOCUMENTID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DOCUMENTNAME TEXT, $COL_CATEGORYID INTEGER REFERENCES $categoryTable)"
      val createTableCoordinate = "CREATE TABLE $coordinateTable ($COL_COORDINATEID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_COORDINATENAME TEXT, $COL_POSX TEXT, $COL_POSY TEXT)"
      val createTableDocumentHave = "CREATE TABLE $documentHaveTable ($COL_DOCUMENTID INTEGER REFERENCES $documentTable, $COL_COORDINATEID INTEGER REFERENCES $coordinateTable, PRIMARY KEY($COL_DOCUMENTID,$COL_COORDINATEID))"
        val createTableFolderHave = "CREATE TABLE $folderHaveTable ($COL_FOLDERID INTEGER REFERENCES $folderTable, $COL_COORDINATEID INTEGER REFERENCES $coordinateTable ,PRIMARY KEY($COL_FOLDERID,$COL_COORDINATEID))"
        val createTableContain = "CREATE TABLE $contain ($COL_FOLDERID INTEGER REFERENCES $folderTable, $COL_DOCUMENTID INTEGER REFERENCES $documentTable,PRIMARY KEY($COL_FOLDERID,$COL_DOCUMENTID))"


        db?.execSQL(createTableCategory)
        db?.execSQL(createTableFolder)
        db?.execSQL(createTableDocument)
        db?.execSQL(createTableCoordinate)
        db?.execSQL(createTableDocumentHave)
        db?.execSQL(createTableFolderHave)
        db?.execSQL(createTableContain)*/

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db);
    }
    //Folder Operations
    @SuppressLint("Range", "Recycle")
    fun getFolderData(): ArrayList<Folder> {
        val result = ArrayList<Folder>()
        val db = this.readableDatabase
        val query = "Select * from $folderTable"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count != 0) {
            println("YAY")
            while (rs.moveToNext()) {
                val id_Category: Int = rs.getString(rs.getColumnIndex(COL_CATEGORYID)).toInt()

                val query2 = "Select * from $categoryTable"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()) {
                    if (rs2.count != 0) {
                        println("YAY2")
                        if (rs2.getString(rs2.getColumnIndex(COL_CATEGORYID))
                                .toInt() == id_Category
                        ) {

                            result.add(
                                Folder(
                                    _idFolder = rs.getInt(rs.getColumnIndex(COL_FOLDERID)),
                                    _nameFolder = rs.getString(rs.getColumnIndex(COL_FOLDERNAME)),
                                    _idCategory = rs.getInt(rs.getColumnIndex(COL_CATEGORYID)),
                                    _nameCategory = rs2.getString(
                                        rs2.getColumnIndex(
                                            COL_CATEGORYNAME
                                        )
                                    )
                                )
                            )

                        }
                    }


                }


            }
        }
        return result

    }

    @SuppressLint("Range")
    fun getFolderFromCategory(_category: String): ArrayList<Folder> {
        val result = ArrayList<Folder>()
        val db = this.readableDatabase
        val query = "Select * from $folderTable"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count != 0) {

            while (rs.moveToNext()) {
                val id_Category: Int = rs.getString(rs.getColumnIndex(COL_CATEGORYID)).toInt()

                val query2 =
                    "Select * from $categoryTable Where $COL_CATEGORYNAME like '%$_category%'"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()) {
                    if (rs2.count != 0) {
                        println("YAY2")
                        if (rs2.getString(rs2.getColumnIndex(COL_CATEGORYID))
                                .toInt() == id_Category
                        ) {

                            result.add(
                                Folder(
                                    _idFolder = rs.getInt(rs.getColumnIndex(COL_FOLDERID)),
                                    _nameFolder = rs.getString(rs.getColumnIndex(COL_FOLDERNAME)),
                                    _idCategory = rs.getInt(rs.getColumnIndex(COL_CATEGORYID)),
                                    _nameCategory = rs2.getString(
                                        rs2.getColumnIndex(
                                            COL_CATEGORYNAME
                                        )
                                    )
                                )
                            )

                        }


                    }


                }


            }
        }
        return result

    }




    //Documents Operations
    @SuppressLint("Range")
    fun getAllDocumentData(_idFolder: Int): ArrayList<Document> {
        val result = ArrayList<Document>()
        val db = this.readableDatabase
        val query = "Select * from $contain Where $COL_FOLDERID = $_idFolder"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count == 0) {
            println("No Data")
        } else {
            while (rs.moveToNext()) {
                println("YAYDocument")
                val idDocument: Int = rs.getString(rs.getColumnIndex(COL_DOCUMENTID)).toInt()
                val query2 = "Select * from $documentTable"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()) {
                    if (rs2.getString(rs2.getColumnIndex(COL_DOCUMENTID)).toInt() == idDocument) {
                        val idCategory: Int = rs2.getString(rs2.getColumnIndex(COL_CATEGORYID))
                            .toInt()
                        val query3 = "Select * from $categoryTable"
                        var rs3: Cursor? = null
                        rs3 = db.rawQuery(query3, null)
                        while (rs3.moveToNext()) {
                            if (rs3.getString(rs3.getColumnIndex(COL_CATEGORYID))
                                    .toInt() == idCategory
                            ) {
                                result.add(
                                    Document(
                                        _idDoc = rs2.getString(rs2.getColumnIndex(COL_DOCUMENTID))
                                            .toInt(),
                                        _nameDoc = rs2.getString(rs2.getColumnIndex(COL_DOCUMENTNAME)),
                                        _idCategory = rs2.getString(
                                            rs2.getColumnIndex(
                                                COL_CATEGORYID
                                            )
                                        )
                                            .toInt(),
                                        _nameCategory = rs3.getString(
                                            rs3.getColumnIndex(
                                                COL_CATEGORYNAME
                                            )
                                        )
                                    )
                                )
                            }
                        }

                    }
                }
            }
        }


        return result
    }

    @SuppressLint("Range")
    fun getDocumentFromCategory(_idFolder: Int, _category: String): ArrayList<Document>{
        val result = ArrayList<Document>()
        val db = this.readableDatabase
        val query = "Select * from $contain Where $COL_FOLDERID = $_idFolder"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count == 0) {
            println("No Data")
        } else {
            while (rs.moveToNext()) {
                println("YAYDocument")
                val idDocument: Int = rs.getString(rs.getColumnIndex(COL_DOCUMENTID)).toInt()
                val query2 = "Select * from $documentTable"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()) {
                    if (rs2.getString(rs2.getColumnIndex(COL_DOCUMENTID)).toInt() == idDocument) {
                        val idCategory: Int = rs2.getString(rs2.getColumnIndex(COL_CATEGORYID))
                            .toInt()
                        val query3 = "Select * from $categoryTable Where $COL_CATEGORYNAME like '%$_category%'"
                        var rs3: Cursor? = null
                        rs3 = db.rawQuery(query3, null)
                        while (rs3.moveToNext()) {
                            if (rs3.getString(rs3.getColumnIndex(COL_CATEGORYID))
                                    .toInt() == idCategory
                            ) {
                                result.add(
                                    Document(
                                        _idDoc = rs2.getString(rs2.getColumnIndex(COL_DOCUMENTID))
                                            .toInt(),
                                        _nameDoc = rs2.getString(rs2.getColumnIndex(COL_DOCUMENTNAME)),
                                        _idCategory = rs2.getString(
                                            rs2.getColumnIndex(
                                                COL_CATEGORYID
                                            )
                                        )
                                            .toInt(),
                                        _nameCategory = rs3.getString(
                                            rs3.getColumnIndex(
                                                COL_CATEGORYNAME
                                            )
                                        )
                                    )
                                )
                            }
                        }

                    }
                }
            }
        }


        return result
    }


    //Document Coordinate Operation
    @SuppressLint("Range")
    fun getAllDocumentCoordinate(_idDocs:Int):ArrayList<Coordinate>{
        val result = ArrayList<Coordinate>()
        val db = this.readableDatabase
        val query = "Select * from $documentHaveTable Where $COL_DOCUMENTID = $_idDocs"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count != 0){
            while (rs.moveToNext()){
                val idCoord:Int = rs.getString(rs.getColumnIndex(COL_COORDINATEID)).toInt()
                val query2 = "Select * from $coordinateTable"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()){
                    if (rs2.getString(rs2.getColumnIndex(COL_COORDINATEID)).toInt() == idCoord){
                       result.add(Coordinate(
                           _idCoord = rs2.getString(rs2.getColumnIndex(COL_COORDINATEID)).toInt(),
                           _nameCoord = rs2.getString(rs2.getColumnIndex(COL_COORDINATENAME)),
                           _posX =  rs2.getString(rs2.getColumnIndex(COL_POSX)),
                           _posY = rs2.getString(rs2.getColumnIndex(COL_POSY))
                       ))
                    }
                }

            }
        }
        return result
    }


    //Folder Coordinate Operation
    @SuppressLint("Range")
    fun getAllFolderCoordinate(_idFolder:Int):ArrayList<Coordinate>{
        val result = ArrayList<Coordinate>()
        val db = this.readableDatabase
        val query = "Select * from $folderHaveTable Where $COL_FOLDERID = $_idFolder"
        var rs: Cursor? = null
        rs = db.rawQuery(query, null)
        if (rs.count != 0){
            while (rs.moveToNext()){
                val idCoord:Int = rs.getString(rs.getColumnIndex(COL_COORDINATEID)).toInt()
                val query2 = "Select * from $coordinateTable"
                var rs2: Cursor? = null
                rs2 = db.rawQuery(query2, null)
                while (rs2.moveToNext()){
                    if (rs2.getString(rs2.getColumnIndex(COL_COORDINATEID)).toInt() == idCoord){
                        result.add(Coordinate(
                            _idCoord = rs2.getString(rs2.getColumnIndex(COL_COORDINATEID)).toInt(),
                            _nameCoord = rs2.getString(rs2.getColumnIndex(COL_COORDINATENAME)),
                            _posX =  rs2.getString(rs2.getColumnIndex(COL_POSX)),
                            _posY = rs2.getString(rs2.getColumnIndex(COL_POSY))
                        ))
                    }
                }

            }
        }
        return result
    }
}

