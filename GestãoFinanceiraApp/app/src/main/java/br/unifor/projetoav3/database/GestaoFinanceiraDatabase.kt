package br.unifor.projetoav3.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.unifor.projetoav3.model.Money
import br.unifor.projetoav3.model.User

@Database(entities = [User::class , Money::class ] , version = 4)
abstract class GestaoFinanceiraDatabase : RoomDatabase() {
   abstract fun getUserDAO(): UserDAO

   abstract fun getMoneyDAO(): MoneyDAO
}