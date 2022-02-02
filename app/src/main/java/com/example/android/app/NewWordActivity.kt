package com.example.android.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for entering a word.
 */

class NewWordActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        supportActionBar?.apply {
            title = "Add Book"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val addBookView1 = findViewById<EditText>(R.id.new_book)
        val addBookView2 = findViewById<EditText>(R.id.new_publisher)
        val addBookView3 = findViewById<EditText>(R.id.new_year)
        val addBookView4 = findViewById<EditText>(R.id.new_price)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(addBookView1.text) && TextUtils.isEmpty(addBookView2.text) &&
                TextUtils.isEmpty(addBookView3.text) && TextUtils.isEmpty(addBookView4.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                if (addBookView3.text.toString().length == 4) {
                    val book_na = addBookView1.text.toString()
                    val book_pu = addBookView2.text.toString()
                    val book_ye = addBookView3.text.toString()
                    val book_pr = addBookView4.text.toString()

                    replyIntent.putExtra(EXTRA_REPLY, book_na)
                    replyIntent.putExtra(EXTRA_REPLY2, book_pu)
                    replyIntent.putExtra(EXTRA_REPLY3, book_ye)
                    replyIntent.putExtra(EXTRA_REPLY4, book_pr)
                    setResult(Activity.RESULT_OK, replyIntent)
                } else {
                    Toast.makeText(this, addBookView3.text.toString() + "is not year", Toast.LENGTH_LONG).show()
                }
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY2 = "com.example.android.wordlistsql.REPLY2"
        const val EXTRA_REPLY3 = "com.example.android.wordlistsql.REPLY3"
        const val EXTRA_REPLY4 = "com.example.android.wordlistsql.REPLY4"
    }
}
