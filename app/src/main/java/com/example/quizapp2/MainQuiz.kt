package com.example.quizapp2

//Página do Quiz
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizapp2.databinding.ActivityMainQuizBinding
import com.firebase.ui.auth.viewmodel.email.EmailLinkSendEmailHandler

class MainQuiz : AppCompatActivity() {
    //setando variaveis necessárias
    private var mCurrentPosition = 1
    private var mQuestionList: List<Questoes>? = null
    private var numDica = 3
    private var mSelectedOptionPosition = 0
    private var mCorrectAnswers = 0
    private var mUserName: String? = null
    private lateinit var binding: ActivityMainQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionList = Constants.getQuestions()
        setQuestion()

        //BOTÃO DESISTIR
        binding.btnDesista.setOnClickListener{
            //Ao desistir o usuário perderá todos os pontos que marcou e será jogado para a tela final
            mCorrectAnswers = 0
            val intent = Intent(this, Final::class.java)

            //Variaveis para compor a tela final
            intent.putExtra(Constants.USER_NAME, mUserName)
            intent.putExtra(Constants.RESPOSTAS_CORRETAS,mCorrectAnswers)
            intent.putExtra(Constants.TOTAL_QUESTOES, mQuestionList!!.size)
            startActivity(intent)
            finish()
        }

        //BOTÃO ELIMINAR
        binding.btnElimina.setOnClickListener{

            //Se tiver dicas sobrando(3)
            if (numDica > 0) {
                val question = mQuestionList?.get(mCurrentPosition - 1)
                val err = mQuestionList?.get(question!!.certa - 1)          //Questão errada nada mais é do que qualquer poição sem ser a correta
                answerView(err!!.certa, R.drawable.wrong_option_border)
                numDica -= 1                                                //Perde uma dica
            } else{
                Toast.makeText(this,"Sem dicas restantes", Toast.LENGTH_SHORT).show()
            }
        }

        //BOTÃO DICA ESCRITA
        binding.btnDica.setOnClickListener {

            //Vetor com o pais origem de todos os times
            val paises = arrayOf<String>("Espanha","Interior","Alemanha","Alemanha", "Inglaterra","Colombia","Pará","Argentina")
            if(numDica > 0){
                val questao = mQuestionList!![mCurrentPosition - 1]
                if (questao.id == mCurrentPosition){
                    Toast                                                   //As posições do vetores são identicas as perguntas
                        .makeText(this,"Esse time é do(a) ${paises[mCurrentPosition -1]}", Toast.LENGTH_SHORT).show()
                    numDica -= 1

                }
            }
            else{
                Toast.makeText(this,"Sem dicas restantes", Toast.LENGTH_SHORT).show()
            }
         }


    }

    //Função que tem a função de
    //Selecionar uma opção, pinta-la e conferir se está certo
    private fun setQuestion() {
        val questao = mQuestionList!![mCurrentPosition - 1]
        defaultOptionView()
        if(mCurrentPosition == mQuestionList!!.size){
            binding.btnAvancar.text = "Acabar"
        }else{
            binding.btnAvancar.text = "Avançar"
        }

        //Cada textView, img
        binding.txtPergunta.text = questao.alternativa
        binding.imgTime.setImageResource(questao.img)
        binding.opcao1.text = questao.alternativa1
        binding.opcao2.text = questao.alternativa2
        binding.opcao3.text = questao.alternativa3
        binding.opcao4.text = questao.alternativa4
        binding.opcao5.text = questao.alternativa5
    }

    private fun defaultOptionView() {
        val options = listOf<TextView>(
            binding.opcao1,
            binding.opcao2,
            binding.opcao3,
            binding.opcao4,
            binding.opcao5
        )

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this@MainQuiz, R.drawable.default_option_border)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(this@MainQuiz, R.drawable.selected_option_border)
    }

    fun onOptionSelect(view: View) {
        when (view.id) {
            R.id.opcao1 -> selectedOptionView(binding.opcao1, 1)
            R.id.opcao2 -> selectedOptionView(binding.opcao2, 2)
            R.id.opcao3 -> selectedOptionView(binding.opcao3, 3)
            R.id.opcao4 -> selectedOptionView(binding.opcao4, 4)
            R.id.opcao5 -> selectedOptionView(binding.opcao5, 5)
        }
    }
    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> binding.opcao1.background = ContextCompat.getDrawable(this, drawableView)
            2 -> binding.opcao2.background = ContextCompat.getDrawable(this, drawableView)
            3 -> binding.opcao3.background = ContextCompat.getDrawable(this, drawableView)
            4 -> binding.opcao4.background = ContextCompat.getDrawable(this, drawableView)
            5 -> binding.opcao5.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
    fun onSubmit(view: View){
        binding.btnElimina.isClickable = true
        binding.btnDica.isClickable = true
        if(mSelectedOptionPosition == 0){
            mCurrentPosition++
            if(mCurrentPosition <= mQuestionList!!.size){
                setQuestion()
            }
            else{
                val intent = Intent(this, Final::class.java)
                intent.putExtra(Constants.USER_NAME, mUserName)
                intent.putExtra(Constants.RESPOSTAS_CORRETAS,mCorrectAnswers)
                intent.putExtra(Constants.TOTAL_QUESTOES, mQuestionList!!.size)
                startActivity(intent)
                finish()
            }

        }else{
            val question = mQuestionList?.get(mCurrentPosition - 1)
            if(question!!.certa != mSelectedOptionPosition){
                answerView(mSelectedOptionPosition,R.drawable.wrong_option_border)
            }else{
                mCorrectAnswers++
            }
            answerView(question!!.certa,R.drawable.colors)

            if(mCurrentPosition == mQuestionList!!.size){
                binding.btnAvancar.text = "Acabar"
            }
            else{
                binding.btnAvancar.text = "Avançar"
            }
            mSelectedOptionPosition = 0
        }

    }

}