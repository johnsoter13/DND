package edu.uw.jsoter.dnd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        val viewAttributesButton: Button = findViewById(R.id.view_attributes_button)
        viewAttributesButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(applicationContext, AttributeList::class.java)
                startActivity(intent)
            }
        })


    }

}
