package edu.uw.jsoter.dnd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createAttributeButton: Button = findViewById(R.id.create_attribute_button)
        createAttributeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                TableNameDialog.newInstance().show(supportFragmentManager, null)
            }
        })


    }

}
