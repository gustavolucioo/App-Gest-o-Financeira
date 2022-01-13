package br.unifor.projetoav3.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["email"], unique = true)
    ]
)

data class User (
    @PrimaryKey val id:Int? = null,
    @ColumnInfo(name =  "full_name") val fullName: String,
    val username: String,
    val email: String,
    val password:String,
    val phonenumber:String
)