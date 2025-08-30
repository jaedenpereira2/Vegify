package com.example.vegify.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class MealCategory {
    BREAKFAST, LUNCH, DINNER, SNACK
}

@Composable
fun MealCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (MealCategory) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<MealCategory?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Meal Category") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MealCategory.values().forEach { category ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                        Text(
                            text = category.name,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedCategory?.let { onConfirm(it) }
                },
                enabled = selectedCategory != null
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 