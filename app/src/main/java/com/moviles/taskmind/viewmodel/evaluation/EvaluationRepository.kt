package com.moviles.taskmind.viewmodel.evaluation

import com.moviles.taskmind.models.GetEvaluationResponse
import com.moviles.taskmind.network.RetrofitInstance
import retrofit2.Response


class EvaluationRepository {

//    fun getEvaluationsFromLocal(): List<Evaluation> {
//        return listOf(
//            Evaluation(
//                id = 1,
//                name = "Examen Parcial",
//                professor = "Juan Pérez",
//                course = "Matemáticas",
//                weight = "60%",
//                date = "2023-11-15",
//                score = "85",
//                color = "#C8ABFC"
//            ),
//            Evaluation(
//                id = 2,
//                name = "Trabajo Práctico",
//                professor = "María Gómez",
//                course = "Historia",
//                weight = "40%",
//                date = "2023-11-20",
//                score = "92",
//                color = "#ABECBE"
//            ),
//            Evaluation(
//                id = 3,
//                name = "Examen Final",
//                professor = "Carlos Rodríguez",
//                course = "Física",
//                weight = "70%",
//                date = "2023-12-05",
//                score = "78",
//                color = "#FF7B6F"
//            )
//        )
//    }

    suspend fun getEvaluationsFromApi(userId: String?): Response<GetEvaluationResponse> {
        return RetrofitInstance.evaluationApi.getEvaluationsById(userId)
    }

}

//data class Evaluation(
//    val id: Int,
//    val name: String,
//    val professor: String,
//    val course: String,
//    val weight: String,
//    val date: String,
//    val score: String,
//    val color: String
//)