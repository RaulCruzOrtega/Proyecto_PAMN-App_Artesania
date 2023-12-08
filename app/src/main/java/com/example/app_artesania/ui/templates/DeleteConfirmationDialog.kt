package com.example.app_artesania.ui.templates

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteConfirmationDialog(text: String, confirmDelete: (Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { confirmDelete(false) },
        title = { Text(text = "Confirmar eliminación") },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = { confirmDelete(true) }) {
                Text("Sí")
            }
        },
        dismissButton = {
            Button(onClick = { confirmDelete(false) }) {
                Text("No")
            }
        }
    )
}