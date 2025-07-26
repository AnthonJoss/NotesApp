package com.anthony.notesapp

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

class PasswordManager {
    companion object {
        /**
         * enteredPassword Contraseña ingresada por el usuario
         *  correctPassword Contraseña correcta almacenada
         */
        fun verifyPassword(enteredPassword: String, correctPassword: Int): Boolean {
            return try {
                enteredPassword.toInt() == correctPassword
            } catch (e: Exception) {
                false
            }
        }
    }

    /**
     * Campo de entrada de contraseña reutilizable con visibilidad toggle
     * Nos permite ingresar una contraseña y alternar su visibilidad
     */
    @Composable
    fun PasswordInputField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        modifier: Modifier = Modifier,
        isError: Boolean = false,
        errorMessage: String = ""
    ) {

        Column {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label, color = Color.Gray) },
                modifier = modifier,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFFFFC107),
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red
                )
            )

            if (isError && errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }

    /**
     * Diálogo para verificar la contraseña antes de acceder al contenido
     * noteTitle Título de la nota que se quiere acceder
     * orrectPassword Contraseña correcta de la nota (Int)
     * onDismiss Callback cuando se cierra el diálogo
     * onPasswordCorrect Callback cuando la contraseña es correcta
     */
    @Composable
    fun PasswordVerificationDialog(
        noteTitle: String?,
        correctPassword: Int,
        onDismiss: () -> Unit,
        onPasswordCorrect: () -> Unit,
        onDeletingConfirmation: () -> Unit,
        isDeleting : Boolean = false,
    ) {
        var enteredPassword by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        val context = LocalContext.current

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2E2E2E)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (noteTitle != null) {
                        Text(
                            text = noteTitle,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isDeleting){
                            "Ingresa la contraseña para eliminar su secretito"
                        } else {
                            "Ingresa la contraseña para ver el contenido"
                        },
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    PasswordInputField(
                        value = enteredPassword,
                        onValueChange = { newValue ->
                            // Solo permitir números
                            if (newValue.all { it.isDigit() }) {
                                enteredPassword = newValue
                                isError = false
                                errorMessage = ""
                            }
                        },
                        label = "Contraseña",
                        modifier = Modifier.fillMaxWidth(),
                        isError = isError,
                        errorMessage = errorMessage
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Gray
                            )
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                if (verifyPassword(enteredPassword, correctPassword)) {
                                    if (isDeleting){
                                        onDeletingConfirmation()
                                        //Mensaje de se ha elimando tu nota correctamente

                                        Toast.makeText(context, "Nota eliminada", Toast.LENGTH_SHORT).show()


                                    } else {
                                        onPasswordCorrect()
                                    }
                                } else {
                                    isError = true
                                    errorMessage = "Contraseña incorrecta"
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFC107),
                                contentColor = Color.Black
                            ),
                            enabled = enteredPassword.isNotEmpty()
                        ) {
                            if (isDeleting) {
                                Text("Eliminar")
                            } else {
                                Text("Abrir")
                            }
                        }
                    }
                }
            }
        }
    }
}