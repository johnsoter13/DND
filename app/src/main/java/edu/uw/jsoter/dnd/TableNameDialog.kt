package edu.uw.jsoter.dnd

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import android.text.InputType



class TableNameDialog : DialogFragment() {

    private val TAG = "TableNameDialog"

    companion object {
        fun newInstance(): TableNameDialog {
            return TableNameDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setTitle("Name Your Attribute!")
            builder.setPositiveButton("Create") { dialog, id ->

                val createAttributeIntent = Intent(context, CreateAttribute::class.java)

                createAttributeIntent.putExtra("table_name", input.text.toString())

                startActivity(createAttributeIntent)}
            builder.setNegativeButton("Cancel") { dialog, which -> Log.v(TAG, "You clicked cancel! Sad times :(") }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}