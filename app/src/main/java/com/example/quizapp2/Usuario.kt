package com.example.quizapp2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "nome")
    var nome: String?,

    @ColumnInfo(name = "pontuacao")
    var recorde: String?
)