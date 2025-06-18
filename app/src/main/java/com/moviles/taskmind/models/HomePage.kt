package com.moviles.taskmind.models

import com.google.gson.annotations.SerializedName

data class HomePageData(
    @SerializedName("resumenCursos") val courseResumen: CourseStatus,
    @SerializedName("evaluacionesProximas") val evaluationProx: List<EvualuationProx>
)

data class CourseStatus(
    @SerializedName("TotalCursos") val courseTotal: Int,
    @SerializedName("Aprobados") val approve: Int,
    @SerializedName("Pendientes") val pending: Int
)

data class EvualuationProx(
    @SerializedName("ID_TYPE") val idType: Int,
    @SerializedName("NombreEvaluacion") val name: String,
    @SerializedName("FechaEvaluacion") val date: String,
    @SerializedName("Detalle") val details: String,
    @SerializedName("Curso") val courseName: String,
    @SerializedName("Color_Curso") val color: String
)
