package br.unifor.projetoav3.database

import androidx.room.*
import br.unifor.projetoav3.model.User
import br.unifor.projetoav3.model.UserWithMoneys


@Dao // CRUD
interface UserDAO {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun find(id: Int): User

    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(email: String): User

    @Query("SELECT * FROM users")
    fun findAll(): List<User>

    // Faz 2 verificações, primeiro id, depois userID
    @Transaction
    @Query("SELECT * FROM users WHERE id = :id")
    fun findUserWithMoneys(id: Int): UserWithMoneys
}