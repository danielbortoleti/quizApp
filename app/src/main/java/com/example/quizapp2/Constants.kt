package com.example.quizapp2

//Usamos esse objeto para guardar as questões, as suas respectivas respostas, e as imagens das perguntas
//Também guardamos o nome, o número de questões e as respostas certas
object Constants {

    const val USER_NAME = "username"
    const val TOTAL_QUESTOES = "total_questoes"
    const val RESPOSTAS_CORRETAS = "respostas_corretas"

    private const val str = "Como se chama esse time?"
    fun getQuestions(): List<Questoes> {
        return listOf(
            Questoes(
                1,
                str,
                R.drawable.barca,
                "Real Madrid",
                "Santos FC",
                "Manchester United",
                "Dinamo Kiev",
                "Barcelona",
                5
            ),
            Questoes(
                2,
                str,
                R.drawable.pontepreta,
                "Ponte Preta",
                "Bragantino",
                "Chelsea",
                "Bahia",
                "Chapecoense",
                1
            ),
            Questoes(
                3,
                str,
                R.drawable.bvb,
                "Bayer de Munich",
                "Palmeiras",
                "Ferroviária",
                "Boca Juniors",
                "Borussia Dortmund",
                5
            ),
            Questoes(
                4,
                str,
                R.drawable.stpaul,
                "São Paulo",
                "Saint Pauli",
                "Strella Pauli",
                "Stat Pauli",
                "Sant Pauli",
                2
            ),
            Questoes(
                5,
                str,
                R.drawable.leeds,
                "Leicester City",
                "Liverpool",
                "Leeds United",
                "Londrina FC",
                "Leeds Union",
                3
            ),
            Questoes(
                6,
                str,
                R.drawable.mili,
                "Monte Azul FC",
                "Marrocos FC",
                "Manchester City",
                "Millonarios FC",
                "Mirassol FC",
                4
            ),
            Questoes(
                7,
                str,
                R.drawable.paysa,
                "Paris Saint Germant",
                "Petrolina Sport Club",
                "Payssandu Sport Club",
                "Palermo",
                "Palmeiras",
                3
            ),
            Questoes(
                8,
                str,
                R.drawable.elp,
                "Estudiants de La Plata",
                "Emirados de La Poente",
                "Escola de La Plata",
                "Eibar FC",
                "Corinthians",
                1
            ),
        )
    }

}