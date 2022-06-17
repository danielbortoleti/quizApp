package com.example.quizapp2

//Página de quando acabar o quiz
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.quizapp2.databinding.ActivityFinalBinding
import com.example.quizapp2.databinding.ActivityMainQuizBinding
import com.example.room.DatabaseDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Final : AppCompatActivity() {

    lateinit var database:DatabaseReference
    private lateinit var binding: ActivityFinalBinding
    private val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Pegando o nome do Usuario no Firebase
        setupFirebase()
        val user = FirebaseAuth.getInstance().currentUser
        val usuario = user?.displayName

        //Pegando o total e o número de questões corretas
        val recorde = intent.getIntExtra(Constants.RESPOSTAS_CORRETAS, 0)
        val questoesTotais = intent.getIntExtra(Constants.TOTAL_QUESTOES, 0)
        binding.txtRecorde.text = "$recorde de $questoesTotais"

        //Lógica de texto final
        if(recorde == 0)
            binding.txtMsg.text = "Oloco $usuario, ai não dá!"
        if(recorde.toInt() >= 1 && recorde.toInt() <= 5)
            binding.txtMsg.text = "É $usuario, poderia ter ido melhor..."
        if(recorde.toInt() >= 6 && recorde.toInt() <= 7)
            binding.txtMsg.text = "Boa $usuario, ta manjando pae"
        if(recorde == 8)
            binding.txtMsg.text = "PELAS BARBAS DO PROFÉTA $usuario, tu é o brabo"

        //BOTÃO SALVAR
        binding.btnSalvar.setOnClickListener{
            //Atribui valores de corretas e nome do jogador
            val acerto = Pontuacao(usuario = user?.displayName.toString(), pontuacao = recorde.toString())
            val jogador = Usuario(nome = acerto.usuario, recorde = acerto.pontuacao)

            //Guardando dados no Database do Room
            val thread = Thread {
                var db = Room.databaseBuilder(this, DatabaseDao::class.java, "AppDb").build()
                var pontuacaoMenor = db.usuarioFut().getByUser(nome = jogador.nome)                         //Pontuação q vai ser substituida pela quebra de recorde

                //Caso o novo record for maior que o antigo
                if(pontuacaoMenor != null){
                    if(jogador.recorde.toString().toInt() > pontuacaoMenor.recorde.toString().toInt()) {
                        pontuacaoMenor.recorde = jogador.recorde
                        //Da update no DatabaseDao
                        db.usuarioFut().update(pontuacaoMenor)
                    }
                }
                else{
                    //Da update no DatabaseDao
                    db.usuarioFut().insert(jogador)
                }
            }
            thread.start()
        }

        //BOTÃO MENU
        binding.btnMenu.setOnClickListener{
            //intent para a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    //Pega os dados do Usuario no FB
    fun setupFirebase(){
        if (user != null) {
            database = FirebaseDatabase.getInstance().reference.child(user.uid)
        }
    }
}