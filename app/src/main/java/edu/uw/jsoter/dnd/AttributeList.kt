package edu.uw.jsoter.dnd

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter

class AttributeList : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attribute_list)

        dbHelper = FeedReaderDbHelper(this)
        db = dbHelper.writableDatabase

        getDatabaseTables()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        adapter = ArrayAdapter(this, R.layout.list_item, R.id.txtItem, values)
        val listView = findViewById<AdapterView<ArrayAdapter<String>>>(R.id.attribute_list_view)
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(applicationContext, CreateAttribute::class.java)
            intent.putExtra("table_name", values[position])
            startActivity(intent)
        }
        listView.adapter = adapter
    }

    private fun getDatabaseTables() {
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)

        if(cursor != null) {
            if(cursor.moveToNext()) {
                do {
                    val table = cursor.getString(cursor.getColumnIndex("name"))
                    values.add(table)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
    }

}
