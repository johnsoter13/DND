package edu.uw.jsoter.dnd

import android.content.ContentValues
import android.content.Intent
import android.database.SQLException
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
    // TODO: fix list view, allow ability to delete values, delete random init tables
    private val TAG = "CreateAttributeActivity"
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()
    private lateinit var table: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_attribute)


        dbHelper = FeedReaderDbHelper(this)
        db = dbHelper.writableDatabase
        // create table if it's not null
        if(intent.getStringExtra("table_name") != null) {
            table = intent.getStringExtra("table_name").toLowerCase()
            // check to see if table is already created in the past
            try {
                val cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name = '$table'",
                    null
                )
                if (cursor.moveToFirst()) {
                    getInitialEntities(table)
                }
                cursor.close()
            } catch (e : SQLException) {
                Log.e(TAG, e.toString())

            }

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS $table (" + "${BaseColumns._ID} INTEGER PRIMARY KEY," + "$table TEXT )"
            )

            val addButton = findViewById<Button>(R.id.add_item_button)
            val addText = findViewById<EditText>(R.id.add_item_text)
            addButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    adapter.add(addText.text.toString())
                    addEntityToDB(addText.text.toString())
                }
            })

            setUpAdapter()
        }
    }

    private fun setUpAdapter() {
        adapter = ArrayAdapter(this, R.layout.list_item, R.id.txtItem, values)
        val listView = findViewById<AdapterView<ArrayAdapter<String>>>(R.id.list_view)
        listView.adapter = adapter
    }

    private fun getInitialEntities(tableName: String) {
        val cursor = db.rawQuery("Select $tableName from $tableName", null)
        if(cursor != null) {
            if(cursor.moveToNext()) {
                do {
                    val entity = cursor.getString(cursor.getColumnIndex(tableName))
                    values.add(entity)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
    }

    private fun addEntityToDB(entity: String) {
        val values = ContentValues().apply {
            put(table, entity)
        }
        val newRowId = db?.insert(table, null, values)

        Log.v(TAG, newRowId.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}
