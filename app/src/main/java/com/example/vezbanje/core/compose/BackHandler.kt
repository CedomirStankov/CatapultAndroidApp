package com.example.vezbanje.core.compose

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CustomBackHandler(navController: NavController, targetRoute: String) {
    BackHandler {
        navController.navigate(targetRoute) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}


@Composable
fun ConfirmExitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirm Exit")
        },
        text = {
            Text("Are you sure you want to exit?")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}

