package com.moviles.taskmind.viewmodel.pdf

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.taskmind.components.course.createPdfPart
import com.moviles.taskmind.network.RetrofitInstance
import com.moviles.taskmind.viewmodel.CourseViewModel
import kotlinx.coroutines.launch

data class PdfUploadUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String? = null
)
class PdfUploadViewModel(

) : ViewModel() {

    var uiState by mutableStateOf(PdfUploadUiState())
        private set

    fun uploadPdf(id: String, pdfBytes: ByteArray,
                  courseViewModel: CourseViewModel
    ) {
        val pdfPart = createPdfPart(pdfBytes, "archivo.pdf")

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isSuccess = false, message = null)

            try {
                val response = RetrofitInstance.pdfApi.uploadPdf(id, pdfPart)
                if (response.isSuccessful) {
                    uiState = uiState.copy(isLoading = false, isSuccess = true)
                    courseViewModel.fetchCourses(id)
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        message = "Error del servidor: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    message = "Error de red: ${e.localizedMessage}"
                )
            }
        }
    }
}
