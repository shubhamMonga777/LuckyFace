package com.example.luckyface.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.luckyface.data.db.entities.CURRENT_USER_ID
import com.example.luckyface.data.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("SELECT * FROM user WHERE uidd = $CURRENT_USER_ID")
    fun getuser(): LiveData<User>

    @Query("DELETE FROM user WHERE uidd = $CURRENT_USER_ID")
    suspend fun deleteUser()
}