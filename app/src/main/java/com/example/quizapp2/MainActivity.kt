package com.example.quizapp2

//MenuPrincipal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp2.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Autenticação do firebase
        if (getUser() == null){
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers).build(),1
            )
        }

        //BOTÃO INICIAR
        binding.btnIniciar.setOnClickListener {
            //intent da MainQuiz
            val i = Intent(this, MainQuiz::class.java)
            startActivity(i)
        }

        //BOTÃO RECORDE
        binding.btnRecorde.setOnClickListener {
            //intent da Recorde
            val i = Intent(this, Recorde::class.java)
            startActivity(i)
        }

        //BOTÃO DESLOGAR
        binding.btnDeslogar.setOnClickListener {
            //Desloga e recarrega a página
            FirebaseAuth.getInstance().signOut();
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    //Função do firebase pra pegar o usuario logado
    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Se estiver autenticado, loga, se não, acaba
        if(requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(this, "User auth", Toast.LENGTH_LONG).show()
        }
        else{
            finishAffinity()
        }
    }
}