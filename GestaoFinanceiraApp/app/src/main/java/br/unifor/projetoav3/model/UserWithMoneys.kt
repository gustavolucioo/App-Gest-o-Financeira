package br.unifor.projetoav3.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithMoneys (
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val moneys: List<Money>

)