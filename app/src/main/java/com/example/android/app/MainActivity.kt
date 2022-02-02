package com.example.android.app

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    lateinit var words: Word
    lateinit var recyclerView: RecyclerView
    lateinit var name_book: EditText
    lateinit var publ_book: EditText
    lateinit var year_book: EditText
    lateinit var pric_book: EditText
    lateinit var saved_button: Button
    lateinit var cancel_button: Button

    lateinit var the_book: String
    lateinit var the_publisher: String
    lateinit var the_year: String
    lateinit var the_price: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "Books"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        recyclerView = findViewById(R.id.recyclerview)

        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(owner = this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var adapter = recyclerView.adapter as WordListAdapter
        words = adapter.word
        if (item.itemId == 0) {
            displayEdit(words)
        } else if (item.itemId == 1) {
            displayDelete(words)
        } else if (item.itemId == 2) {
            displayDeleteAll()
        } else {
            displayError()
        }
        return super.onContextItemSelected(item)
    }

    private fun displayDeleteAll() {
        wordViewModel.deleteAll()
        Toast.makeText(this@MainActivity, "All Data deleted", Toast.LENGTH_LONG).show()
    }

    private fun displayError() {
        Toast.makeText(this@MainActivity, "Error in clicking", Toast.LENGTH_LONG).show()
    }

    private fun displayDelete(thisWord: Word) {
        wordViewModel.delete(thisWord)
        Toast.makeText(this@MainActivity,  words.book + " Deleted", Toast.LENGTH_LONG).show()
    }

    private fun displayEdit(thisWord: Word) {
        val dialog = Dialog(this)
        dialog.setTitle("Edit")
        dialog.setContentView(R.layout.edit_activity)
        dialog.setCancelable(true)

        name_book = dialog.findViewById(R.id.name_book) as EditText
        publ_book = dialog.findViewById(R.id.publisher_book) as EditText
        year_book = dialog.findViewById(R.id.year_book) as EditText
        pric_book = dialog.findViewById(R.id.price_book) as EditText

        saved_button = dialog.findViewById(R.id.save_edit) as Button
        cancel_button = dialog.findViewById(R.id.cancel_edit) as Button

        name_book.setText(thisWord.book)
        publ_book.setText(thisWord.publisher)
        year_book.setText(thisWord.year)
        pric_book.setText(thisWord.price)

        saved_button.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(name_book.text) ||
                TextUtils.isEmpty(publ_book.text) ||
                TextUtils.isEmpty(year_book.text) ||
                TextUtils.isEmpty(pric_book.text) ) {

                    Toast.makeText(this, "Cannot be blank", Toast.LENGTH_SHORT).show()

            } else {

                thisWord.book = name_book.text.toString()
                thisWord.publisher = publ_book.text.toString()
                thisWord.year = year_book.text.toString()
                thisWord.price = pric_book.text.toString()

                wordViewModel.update(thisWord)
                dialog.dismiss()
            }
        })
        cancel_button.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == RESULT_OK) {
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply -> the_book = reply }
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY2)?.let { reply2 -> the_publisher = reply2 }
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY3)?.let { reply3 -> the_year = reply3 }
            intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY4)?.let { reply4 -> the_price = reply4 }
            val x = Word(0, the_book, the_publisher, the_year, the_price)
            wordViewModel.insert(x)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
