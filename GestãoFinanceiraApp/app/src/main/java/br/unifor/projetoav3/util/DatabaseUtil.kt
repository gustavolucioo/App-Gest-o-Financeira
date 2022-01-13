package br.unifor.projetoav3.util

import android.content.Context
import androidx.room.Room
import br.unifor.projetoav3.database.GestaoFinanceiraDatabase
import java.security.AccessControlContext

object DatabaseUtil {
    private var db: GestaoFinanceiraDatabase? = null
    fun getInstance(context: Context): GestaoFinanceiraDatabase{
        if(db == null){
            db = Room.databaseBuilder(
                context,
                GestaoFinanceiraDatabase::class.java,
                "GestaoFinanceiraDB").
            fallbackToDestructiveMigration().build()
        }
        return db!!
    }
}