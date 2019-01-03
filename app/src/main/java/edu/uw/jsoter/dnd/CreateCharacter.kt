package edu.uw.jsoter.dnd

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter

class CreateCharacter : AppCompatActivity() {
    private val TAG = "CreateCharacter"
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_character)

        dbHelper = CharacterDatabaseHelper(this)
        db = dbHelper.writableDatabase

        if(intent.getStringExtra("character_name") != null) {
            character = Character(intent.getStringExtra("character_name"))

            try {
                val cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM Characters WHERE CharacterName = '${character.name}'",
                    null
                )
                if (cursor.moveToFirst()) {
                    getInitialAttributes()
                }
                cursor.close()
            } catch (e : SQLException) {
                Log.e(TAG, e.toString())

            }
        }

        setUpAdapter()
    }
    private fun getInitialAttributes() {

    }

    private fun setUpAdapter() {
        adapter = ArrayAdapter(this, R.layout.list_item, R.id.txtItem, values)
        val listView = findViewById<AdapterView<ArrayAdapter<String>>>(R.id.character_attribute_list_view)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

        }
        listView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.character_menu, menu) // Inflate the menu resource
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle menu item clicks
        return when (item.itemId) {
            R.id.add_attribute_btn -> {
                val fragment = ViewAttributesFragment.newInstance()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.container, fragment)
                ft.commit()
                true
            }
            R.id.save_btn -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
