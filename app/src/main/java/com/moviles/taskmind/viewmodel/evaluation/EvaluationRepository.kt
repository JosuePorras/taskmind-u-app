package com.moviles.taskmind.viewmodel.evaluation


class EvaluationRepository {

    fun getEvaluationsFromLocal(): List<Evaluation> {
        return listOf(
            Evaluation(
                id = 1,
                name = "Examen Parcial",
                course = "Matemáticas",
                weight = "60%",
                date = "2023-11-15",
                score = "85"
            ),
            Evaluation(
                id = 2,
                name = "Trabajo Práctico",
                course = "Historia",
                weight = "40%",
                date = "2023-11-20",
                score = "92"
            ),
            Evaluation(
                id = 3,
                name = "Examen Final",
                course = "Física",
                weight = "70%",
                date = "2023-12-05",
                score = "78"
            )
        )
    }
}

data class Evaluation(
    val id: Int,
    val name: String,
    val course: String,
    val weight: String,
    val date: String,
    val score: String
)