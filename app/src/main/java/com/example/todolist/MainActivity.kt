package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        findViewById<Button>(R.id.button).setOnClickListener{
            Log.i("Caren", "user clicked on button");
        }*/
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClickListener(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItem()
            }

        }

        //listOfTasks.add("laundry")
        //listOfTasks.add("walk")
        loadItems()

        //val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
         adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.AddTaskField)

        findViewById<Button>(R.id.button).setOnClickListener{
            val userInputtedTask = inputTextField.text.toString()

            listOfTasks.add(userInputtedTask)

            adapter.notifyItemInserted(listOfTasks.size - 1)

            inputTextField.setText("")

        }
    }

    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    fun loadItems()
    {


        try
            {
                listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException : Exception)
        {
            ioException.printStackTrace()
        }
    }
    /*
    fun saveItems()
    {
        try {
            //FileUtils.writeLines(getDataFile(), listOfTasks)
            FileUtils.writeLines()
        } catch(ioException : Exception)
        {
            ioException.printStackTrace()
        }

    }*/

    fun saveItem()
    {
        try
            {
                org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
            }catch(ioException : Exception)
            {
                ioException.printStackTrace()
            }

    }

}