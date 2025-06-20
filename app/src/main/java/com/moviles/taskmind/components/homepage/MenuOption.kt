package com.moviles.taskmind.components.homepage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun MenuOption(
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = onDelete,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
            border = BorderStroke(1.dp, Color.Red),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(36.dp)
                .fillMaxWidth()
        ) {
            Text("Eliminar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 6.dp),
            color = Color(0xFFDDDDDD),
            thickness = 1.dp
        )

        OutlinedButton(
            onClick = onEdit,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
            border = BorderStroke(1.dp, Color.Blue),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(36.dp)
                .fillMaxWidth()
        ) {
            Text("Editar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}


@Composable
fun MenuOptionPopup(
    expanded: Boolean,
    anchorOffset: IntOffset = IntOffset(0, 0),
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    if (expanded) {
        Popup(
            offset = anchorOffset,
            onDismissRequest = onDismiss,
            properties = PopupProperties(focusable = true)
        ) {
            MenuOption(
                onDelete = onDelete,
                onEdit = onEdit
            )
        }
    }
}

