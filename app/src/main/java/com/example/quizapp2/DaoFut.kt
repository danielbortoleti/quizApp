package com.example.quizapp2

//Parte Room exigida
//Usamos Room para guardar os recordes e os usuários, para existir a funcionalidade de ver os recordes dos demais players


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoFut {
    @Query("Select * from Usuario")
    fun getAll(): List<Usuario>

    @Query("Select * from Usuario where nome = :nome")
    fun getByUser(nome: String?): Usuario

    @Insert
    fun insert(user: Usuario)

    @Update
    fun update(user: Usuario)
}