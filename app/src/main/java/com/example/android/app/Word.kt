package com.example.android.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
                @ColumnInfo(name = "book") var book: String,
                @ColumnInfo(name = "publisher") var publisher: String,
                @ColumnInfo(name = "year") var year: String,
                @ColumnInfo(name = "price") var price: String
)

