package edu.uw.jsoter.dnd

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

class CreateAttribute : AppCompatActivity() {
    private val TAG = "CreateAttributeActivity"
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_attribute)


        dbHelper = FeedReaderDbHelper(this)
        db = dbHelper.writableDatabase

        // create table if it's not null
        if(intent.getStringExtra("table_name") != null) {
            db.execSQL("CREATE TABLE IF NOT EXISTS ${intent.getStringExtra("table_name")} (" + "${BaseColumns._ID} INTEGER PRIMARY KEY," + "${intent.getStringExtra("table_name")} TEXT )")
            Log.v(TAG, intent.getStringExtra("table_name"))
        }

        val addButton = findViewById<Button>(R.id.add_item_button)
        val addText = findViewById<EditText>(R.id.add_item_text)
        addButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                adapter.add(addText.text.toString())
                addEntityToDB(addText.text.toString())
            }
        })

        adapter = ArrayAdapter(this, R.layout.list_item, R.id.txtItem, values)
        val listView = findViewById<AdapterView<ArrayAdapter<String>>>(R.id.list_view)
        listView.adapter = adapter
    }

    private fun addEntityToDB(entity: String) {
        val column = intent.getStringExtra("table_name")
        val values = ContentValues().apply {
            put(column, entity)
        }
        val newRowId = db?.insert(column, null, values)

        Log.v(TAG, newRowId.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
