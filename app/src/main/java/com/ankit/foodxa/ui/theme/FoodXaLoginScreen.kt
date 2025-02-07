package com.ankit.foodxa.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodXaLoginScreen(
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    // Add focus management
    val focusManager = LocalFocusManager.current
    val emailFieldFocusRequester = remember { FocusRequester() }
    val passwordFieldFocusRequester = remember { FocusRequester() }

    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Custom TextField Colors
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFF2A2A2A),
        unfocusedContainerColor = Color(0xFF2A2A2A),
        disabledContainerColor = Color(0xFF2A2A2A),
        focusedBorderColor = Color(0xFFF97316),
        unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        disabledTextColor = Color.White,
        focusedLeadingIconColor = Color(0xFFF97316),
        unfocusedLeadingIconColor = Color.Gray,
        focusedLabelColor = Color(0xFFF97316),
        unfocusedLabelColor = Color.Gray,
        cursorColor = Color(0xFFF97316)
    )

    // Add scroll state
    val scrollState = rememberScrollState()
    // Calculate scroll progress for status bar fade
    val statusBarAlpha by remember {
        derivedStateOf {
            (scrollState.value.toFloat() / 600f).coerceIn(0f, 1f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E1E1E),
                        Color(0xFF121212)
                    )
                )
            )
    ) {
        // Add status bar scrim
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .graphicsLayer {
                    alpha = statusBarAlpha
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E1E1E),
                            Color(0xFF1E1E1E).copy(alpha = 0f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .padding(horizontal = 30.dp)
                .padding(top = 60.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo and App Name
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF97316).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "FoodXa",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    )
                    Text(
                        text = "Food Delivery Service",
                        style = TextStyle(
                            color = Color(0xFFF97316),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // Login Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome Back",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Login to continue",
                    style = TextStyle(
                        color = Color(0xFFF97316),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Email Field
            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "Email Icon",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                label = {
                    Text(
                        "Email Address",
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                singleLine = true,
                colors = textFieldColors,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 4.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0xFFF97316).copy(alpha = 0.1f)
                    ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFieldFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = passwordState,
                onValueChange = { passwordState = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Password Icon",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                            tint = if (passwordVisible) Color(0xFFF97316) else Color.Gray
                        )
                    }
                },
                label = {
                    Text(
                        "Password",
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                singleLine = true,
                colors = textFieldColors,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 4.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0xFFF97316).copy(alpha = 0.1f)
                    ),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Login Button
            Button(
                onClick = {
                    isLoggingIn = true
                    // Simulate login process with a delay
                    android.os.Handler().postDelayed({
                        onLoginSuccess()
                    }, 1500) // 1.5 seconds delay
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0xFFF97316).copy(alpha = 0.2f)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF97316),
                    disabledContainerColor = Color(0xFFF97316).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoggingIn && emailState.isNotEmpty() && passwordState.isNotEmpty()
            ) {
                if (isLoggingIn) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Login",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sign Up Link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 16.sp
                    )
                )
                TextButton(onClick = onSignUpClick) {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            color = Color(0xFFF97316),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

// Handle login process
private fun handleLogin(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    // Add your login logic here
    // For now, we'll just simulate a successful login
    onSuccess()
} 