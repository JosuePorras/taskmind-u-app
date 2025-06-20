package com.moviles.taskmind.components.course

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun FilePicker(
    onPdfSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let { onPdfSelected(it) }
        }
    )

    Button(
        onClick = {
            launcher.launch(arrayOf("application/pdf"))
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEA4335) // Rojo estilo Google/PDF
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
    ) {
        Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = "Seleccionar PDF",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text("Seleccionar PDF", color = Color.White, fontSize = 16.sp)
    }
}



fun createPdfPart(pdfBytes: ByteArray, fileName: String): MultipartBody.Part {
    val requestBody = pdfBytes.toRequestBody("application/pdf".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("pdf", fileName, requestBody)
}
fun readPdfAsByteArray(context: Context, uri: Uri): ByteArray {
    return context.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: ByteArray(0)
}

/*
@Composable
fun UploadPdfScreen(userId: String, apiService: ApiService) {
    val context = LocalContext.current

    FilePicker { uri ->
        val pdfBytes = readPdfAsByteArray(context, uri)
        val pdfPart = createPdfPart(pdfBytes, "archivo.pdf")

        // Coroutine para llamada Retrofit
        LaunchedEffect(pdfBytes) {
            val response = apiService.uploadPdf(userId, pdfPart)
            if (response.isSuccessful) {
                Log.d("PDF", "Subido con éxito")
            } else {
                Log.e("PDF", "Error al subir")
            }
        }
    }
}*/
