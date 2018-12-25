package edu.uw.jsoter.dnd

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

class CreateAttribute : AppCompatActivity() {
    private val TAG = "CreateAttributeActivity"
    private lateinit var createEntries: String
    private lateinit var deleteEntries: String
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_attribute)
        if(intent.getStringExtra("create_entries") != null && intent.getStringExtra("delete_entries") != null) {
            createEntries = intent.getStringExtra("create_entries")
            deleteEntries = intent.getStringExtra("delete_entries")
        }

        dbHelper = FeedReaderDbHelper(this, createEntries, deleteEntries)

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
        val db = dbHelper.writableDatabase
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
