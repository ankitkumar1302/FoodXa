package com.ankit.foodxa.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FoodXaSignUpScreenFunctional(
    onLoginClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {}
) {
    // Add focus management
    val focusManager = LocalFocusManager.current
    val nameFieldFocusRequester = remember { FocusRequester() }
    val emailFieldFocusRequester = remember { FocusRequester() }
    val passwordFieldFocusRequester = remember { FocusRequester() }

    // State variables
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var isSigningUp by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Handle sign up process with loading animation
    LaunchedEffect(isSigningUp) {
        if (isSigningUp) {
            kotlinx.coroutines.delay(1500)
            isSigningUp = false
            onSignUpSuccess()
        }
    }

    // Make sure text is white on a dark background
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
        cursorColor = Color(0xFFF97316),
        focusedPlaceholderColor = Color.Gray,
        unfocusedPlaceholderColor = Color.Gray
    )

    // Get keyboard state
    val density = LocalDensity.current
    val imeVisible = WindowInsets.ime.getBottom(density) > 0
    val imeAnimation by animateFloatAsState(
        targetValue = if (imeVisible) 1f else 0f,
        label = "ime_animation"
    )

    // Add scroll state
    val scrollState = rememberScrollState()
    // Calculate scroll progress for status bar fade
    val statusBarAlpha by remember {
        derivedStateOf {
            (scrollState.value.toFloat() / 600f).coerceIn(0f, 1f)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF1E1E1E).copy(alpha = 0.9f),
                                Color(0xFF1E1E1E)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                // Enhanced Sign Up Button
                Button(
                    onClick = {
                        if (nameState.isNotBlank() && emailState.isNotBlank() && passwordState.isNotBlank()) {
                            isSigningUp = true
                        }
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
                    enabled = !isSigningUp && nameState.isNotEmpty() &&
                            emailState.isNotEmpty() && passwordState.isNotEmpty()
                ) {
                    if (isSigningUp) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Create Account",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
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
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .statusBarsPadding()
                    .padding(horizontal = 30.dp)
                    .padding(top = 60.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo and App Name with enhanced styling
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

                // Sign Up Header with enhanced styling
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Sign Up",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Create your account to get started",
                        style = TextStyle(
                            color = Color(0xFFF97316),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Name Input Field
                OutlinedTextField(
                    value = nameState,
                    onValueChange = { nameState = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Name Icon",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    label = { Text("Full Name") },
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
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { emailFieldFocusRequester.requestFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input Field
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
                    label = { Text("Email Address") },
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

                // Password Input Field
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
                                if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                tint = if (passwordVisible) Color(0xFFF97316) else Color.Gray
                            )
                        }
                    },
                    label = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (nameState.isNotBlank() && emailState.isNotBlank() && passwordState.isNotBlank()) {
                                isSigningUp = true
                            }
                        }
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Login Link moved here
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = TextStyle(
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    )
                    TextButton(onClick = onLoginClick) {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                color = Color(0xFFF97316),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Add extra space at the bottom to ensure content is not hidden by the bottom bar
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// Email validation function remains unchanged
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun FoodXaSignUpScreenFunctionalPreview() {
    FoodXaSignUpScreenFunctional()
}