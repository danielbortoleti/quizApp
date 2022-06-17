package com.example.quizapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.quizapp2.databinding.ActivityFinalBinding
import com.example.quizapp2.databinding.ActivityRecordeBinding
import com.example.quizapp2.databinding.CardBinding
import com.example.room.DatabaseDao

class Recorde : AppCompatActivity() {
    private lateinit var binding: ActivityRecordeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = Thread {
            var db = Room.databaseBuilder(this, DatabaseDao::class.java, "AppDb").build()
            var total = db.usuarioFut().getAll()

            runOnUiThread {
                binding.container.removeAllViews()

                total.forEach{
                    val cardBinding = CardBinding.inflate(layoutInflater)

                    cardBinding.txtnome.text = it.nome.toString()
                    cardBinding.txtpontuacao.text = it.recorde.toString()

                    binding.container.addView(cardBinding.root)
                }
            }
        }
        thread.start()
    }
    fun refreshUI(list: List<Pontuacao>){
        binding.container.removeAllViews()

        list.forEach{
            val cardBinding = CardBinding.inflate(layoutInflater)

            cardBinding.txtnome.text = it.usuario
            cardBinding.txtpontuacao.text = it.pontuacao.toString()

            binding.container.addView(cardBinding.root)
        }
    }
}