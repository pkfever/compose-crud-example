package com.test.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import androidx.room.Delete
import com.test.myapplication.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE email =:email LIMIT 1")
    suspend fun findByEmail(email: String): User?

    @Query("SELECT * FROM user_table WHERE id =:id LIMIT 1")
    suspend fun userById(id: Int): User

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * from user_table ORDER BY id ASC")
    fun readAllData(): Flow<List<User>>
}