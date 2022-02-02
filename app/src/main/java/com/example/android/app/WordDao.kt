package com.example.android.app

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY book ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Update
    suspend fun update(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}
