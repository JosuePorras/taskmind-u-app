package com.moviles.taskmind.utils

import com.moviles.taskmind.models.Evaluation

fun ProgressCalculator(evaluaciones: List<Evaluation>?): Double {
    return evaluaciones?.sumOf { evaluation ->
        val nota = evaluation.studentEvaluations.firstOrNull()?.scoreObtained
        if (nota != null) (nota * evaluation.weight / 100) else 0.0
    }?:0.0
}
