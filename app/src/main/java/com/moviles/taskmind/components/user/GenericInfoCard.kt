package com.moviles.taskmind.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsCard(
    title: String? = null,
    items: List<SettingItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            title?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDBEAFE))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            }

            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                items.forEachIndexed { index, item ->
                    SettingItemRow(
                        item = item,
                        showDivider = index < items.lastIndex
                    )
                }
            }
        }
    }
}
data class SettingItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconTint: Color = Color(0xFF2BD4BD),
    val iconBackground: Color = Color(0xFF9DF3AF).copy(alpha = 0.2f),
    val hasToggle: Boolean = true,
    val isChecked: Boolean = false,
    val onCheckedChange: (Boolean) -> Unit = {}
)

@Composable
private fun SettingItemRow(
    item: SettingItem,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = item.iconBackground,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = item.iconTint
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (item.hasToggle) {
                Switch(
                    checked = item.isChecked,
                    onCheckedChange = item.onCheckedChange,
                    modifier = Modifier.scale(1.0f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Black,
                        uncheckedThumbColor = Color(0xFF9EACAE),
                        uncheckedTrackColor = Color(0xFFE7EAEB)
                    )
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        }
    }
}

