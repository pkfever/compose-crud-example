package com.test.myapplication.data

import androidx.room.*
import com.test.myapplication.data.entities.User

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

    @Query("SELECT * from user_table ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    suspend fun readAllData(): List<User> // <- This means function return type is List. Specifically, a List of Users.
}