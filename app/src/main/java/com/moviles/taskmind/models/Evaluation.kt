package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName



data class GetEvaluationResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("evaluations") val evaluations: List<Evaluation>
)


data class Evaluation(
    @SerializedName("ID_TYPE") val typeId: Int,
    @SerializedName("ID_COURSE") val courseId: Int,
    @SerializedName("DSC_NAME") val name: String,
    @SerializedName("WEIGHT") val weight: Int,
    @SerializedName("DATE_EVALUATION") val date: String, // puedes parsear a LocalDate si usas ThreeTen o java.time
    @SerializedName("DSC_EVALUATION") val description: String,
    @SerializedName("ID_USER") val userId: Int,
    @SerializedName("Course") val course: CourseEvaluation,
    @SerializedName("StudentEvaluations") val studentEvaluations: List<Any>,
    @SerializedName("user") val user: UserEvaluation
)


data class CourseEvaluation(
    @SerializedName("ID_COURSE") val id: Int,
    @SerializedName("DSC_NAME") val name: String,
    @SerializedName("ID_TEACHER") val teacherId: Int,
    @SerializedName("ID_USER") val userId: Int,
    @SerializedName("DSC_CODE") val code: String,
    @SerializedName("DSC_ATTENTION") val attention: String,
    @SerializedName("DSC_COLOR") val color: String,
    @SerializedName("Professor") val professor: ProfessorEvaluation
)


data class ProfessorEvaluation(
    @SerializedName("DSC_FIRST_NAME") val firstName: String,
    @SerializedName("DSC_LAST_NAME_ONE") val lastNameOne: String,
    @SerializedName("DSC_LAST_NAME_TWO") val lastNameTwo: String,
    @SerializedName("DSC_EMAIL") val email: String,
    @SerializedName("DSC_PHONE") val phone: String
)


data class UserEvaluation(
    @SerializedName("DSC_FIRST_NAME") val firstName: String,
    @SerializedName("DSC_LAST_NAME_ONE") val lastName: String
)