package edu.uw.jsoter.dnd


import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

/**
 * A simple [Fragment] subclass.
 *
 */
class ViewAttributesFragment : Fragment() {
    private lateinit var dbHelper: SQLiteOpenHelper
    private lateinit var db : SQLiteDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var values = mutableListOf<String>()

    companion object {
        fun newInstance(): ViewAttributesFragment {
            return ViewAttributesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.activity_attribute_list, container, false)

        dbHelper = FeedReaderDbHelper(activity!!)
        db = dbHelper.writableDatabase

        getDatabaseTables()
        setUpAdapter(rootView)

        return rootView
    }
    private fun setUpAdapter(rootView: View?) {
        adapter = ArrayAdapter(activity!!, R.layout.list_item, R.id.txtItem, values)
        val listView = rootView!!.findViewById<AdapterView<ArrayAdapter<String>>>(R.id.attribute_list_view)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            getActivity()!!.supportFragmentManager.beginTransaction().remove(this).commit()
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
