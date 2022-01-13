package br.unifor.projetoav3.database

import androidx.room.*
import br.unifor.projetoav3.model.Money

@Dao
interface MoneyDAO {
    @Insert
    fun insert(money: Money)

    @Update
    fun update(money: Money)

    @Delete
    fun delete(money: Money)

    @Query("SELECT * FROM moneys WHERE id = :id")
    fun find(id: Int): Money

    @Query("SELECT * FROM moneys")
    fun findAll():List<Money>


}