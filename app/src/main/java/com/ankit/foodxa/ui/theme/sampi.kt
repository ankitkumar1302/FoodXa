// MainActivity.kt
package com.ankit.foodxa.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ankit.foodxa.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    // Define custom colors for food app theme
    val primaryOrange = Color(0xFFFF5722)
    val secondaryOrange = Color(0xFFFF8A65)
    val darkBackground = Color(0xFF121212)
    val lightText = Color(0xFFEEEEEE)
    
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = darkBackground,
            darkIcons = false
        )
    }

    // Create a gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(darkBackground, Color(0xFF2D2D2D)),
        startY = 0f,
        endY = 3000f
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title for the top bar */ },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            "Skip",
                            color = lightText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            // Content Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                
                // Top section with logo and tagline
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=987&auto=format&fit=crop",
                        contentDescription = "Food Logo",
                        modifier = Modifier
                            .size(180.dp)
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Fit
                    )
                    
                    Text(
                        text = "Delicious food at your doorstep",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = lightText,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                
                // Middle section with food images
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1571997478779-2adcbbe9ab2f?q=80&w=987&auto=format&fit=crop",
                    contentDescription = "Delicious Food",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                    alpha = 0.9f
                )
                
                // Bottom section with buttons
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 24.dp)
                ) {
                    // "Continue with Google" Button
                    Button(
                        onClick = { /* TODO: Handle Google Login */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color.Red, RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Google",
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }

                    // "Continue with Email" Button
                    Button(
                        onClick = { /* TODO: Handle Email Login */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryOrange),
                    ) {
                        Text(
                            text = "Continue with Email",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                    }

                    // "Create Account" Button
                    Button(
                        onClick = { /* TODO: Handle Account Creation */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF333333)
                        ),
                    ) {
                        Text(
                            text = "Create New Account",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = lightText
                            )
                        )
                    }
                }

                // Privacy Policy and Terms of Service Texts
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            text = "Privacy Policy",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 14.sp,
                                color = lightText,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                    
                    Text(
                        text = "•",
                        color = lightText,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    
                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            text = "Terms of Service",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 14.sp,
                                color = lightText,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginScreen()
    }
}