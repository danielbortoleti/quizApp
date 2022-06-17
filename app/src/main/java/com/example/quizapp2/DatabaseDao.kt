package com.example.room

//Criação do Database do Room, com as função do Dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp2.DaoFut
import com.example.quizapp2.Usuario

@Database(entities = arrayOf(Usuario::class), version = 1)
abstract class DatabaseDao: RoomDatabase() {
    abstract fun usuarioFut():DaoFut
}