package br.unifor.projetoav3.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moneys")
data class Money (
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val isReceita: Boolean,
    val valor: Double,
    @ColumnInfo(name = "user_id")
    val userID:Int
)