package com.moviles.taskmind.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBlueOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    supportingText: @Composable (() -> Unit)? = null,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    cornerRadius: Int = 50
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        shape = RoundedCornerShape(cornerRadius.dp),
        modifier = modifier,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        isError = isError,
        supportingText = supportingText,
        leadingIcon = leadingIcon?.let { icon ->
            {
                Icon(
                    imageVector = icon,
                    contentDescription = leadingIconDescription,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1976D2),
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color(0xFF1976D2),
            cursorColor = Color(0xFF1976D2),
            unfocusedTextColor = Color.Black,
            errorBorderColor = Color(0xFFD32F2F),
            errorLabelColor = Color(0xFFD32F2F),
            errorCursorColor = Color(0xFFD32F2F),
            focusedTextColor = Color.Black,
            errorSupportingTextColor = Color(0xFFD32F2F),
            errorTrailingIconColor = Color(0xFFD32F2F),
            errorLeadingIconColor = Color(0xFFD32F2F)
        )
    )
}