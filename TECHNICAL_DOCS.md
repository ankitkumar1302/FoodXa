# FoodXa - Technical Documentation

## Architecture Overview

FoodXa is a modern food delivery app built with Jetpack Compose, following Material Design 3 principles and implementing a clean, component-based architecture. This document provides a comprehensive technical overview of the codebase.

## Table of Contents

1. [Project Structure](#project-structure)
2. [Technology Stack](#technology-stack)
3. [Key Components](#key-components)
4. [UI Architecture](#ui-architecture)
5. [Navigation Flow](#navigation-flow)
6. [Screen Components](#screen-components)
7. [State Management](#state-management)
8. [Styling Patterns](#styling-patterns)
9. [Implementation Guidelines](#implementation-guidelines)

## Project Structure

```
app/
├── src/main/
│   ├── java/com/ankit/foodxa/
│   │   ├── ui/
│   │   │   ├── theme/
│   │   │   │   ├── AllTransactionsScreen.kt    # Transaction history screen
│   │   │   │   ├── CartScreen.kt               # Shopping cart implementation
│   │   │   │   ├── Color.kt                    # Color definitions
│   │   │   │   ├── FoodXaHomeScreen.kt         # Main home screen
│   │   │   │   ├── FoodXaLoginScreen.kt        # User login screen
│   │   │   │   ├── FoodXaSignUpScreen.kt       # User registration
│   │   │   │   ├── MyCardScreen.kt             # Payment card management
│   │   │   │   ├── PopularFoodScreen.kt        # Featured food items
│   │   │   │   ├── ProfileScreen.kt            # User profile
│   │   │   │   ├── Theme.kt                    # Theme configuration
│   │   │   │   └── Type.kt                     # Typography definitions
│   │   └── MainActivity.kt                     # Main entry point
│   └── res/                                    # Resources
│       ├── drawable/                           # Image resources
│       ├── values/                             # App values
│       └── xml/                                # XML configurations
└── build.gradle.kts                            # App dependencies
```

## Technology Stack

- **Kotlin**: 100% Kotlin codebase (version 1.9.x)
- **Jetpack Compose**: UI toolkit for building native interfaces
- **Material 3**: Implementation of Material Design guidelines
- **Coroutines**: For asynchronous operations
- **Coil**: Image loading library integrated with Compose
- **CompileSdk**: 35
- **MinSdk**: 26
- **TargetSdk**: 35

## Key Components

### 1. MainActivity
The main entry point of the application that handles:
- Initialization of the app and theme
- Navigation between screens
- Back button behavior
- Bottom navigation bar implementation

Key features:
```kotlin
// State management for screen navigation
var currentScreen by remember { mutableStateOf("auth") }
var selectedTab by remember { mutableIntStateOf(0) }

// Bottom navigation implementation
enum class BottomNavItem(val index: Int, val label: String, val icon: ImageVector) {
    Home(0, "Home", Icons.Default.Home),
    Explore(1, "Explore", Icons.Default.Search),
    Cart(2, "Cart", Icons.Default.ShoppingCart),
    Profile(5, "Profile", Icons.Default.Person)
}
```

### 2. Theme Configuration
The app uses a custom dark theme throughout the application:

```kotlin
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFF97316),    // Primary orange color
    secondary = Color(0xFFF97316),
    tertiary = Color(0xFFF97316),
    background = Color(0xFF1E1E1E), // Dark background
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)
```

## UI Architecture

The app follows a component-based UI architecture with the following principles:

### 1. Screen Components
Each screen is implemented as a composable function with explicit navigation callbacks:

```kotlin
@Composable
fun FoodXaHomeScreen(
    onNavigateToPopularFood: () -> Unit = {},
    onNavigateToMyCard: () -> Unit = {}
) {
    // Screen implementation
}
```

### 2. State Hoisting
State is hoisted to appropriate levels and passed down to child components:

```kotlin
var searchQuery by remember { mutableStateOf("") }
var isLoggingIn by remember { mutableStateOf(false) }
```

### 3. Reusable Components
Common UI elements are extracted into reusable composables:

```kotlin
@Composable
fun CategoryItem(category: Category) {
    // Component implementation
}

@Composable
fun FoodCard(food: FoodItem, onAddToCart: () -> Unit = {}) {
    // Component implementation
}
```

## Navigation Flow

The app implements a custom navigation system with two main states:

1. **Authentication Flow**:
   - Login screen
   - Sign-up screen

2. **Main App Flow** (after authentication):
   - Home screen with food categories and popular items
   - Popular food listing screen
   - Cart and checkout screens
   - Payment card management
   - Transaction history
   - User profile

Navigation is handled through callback functions passed to each screen:

```kotlin
// Example of navigation handling in MainActivity
FoodXaLoginScreen(
    onSignUpClick = { showLogin = false },
    onLoginSuccess = { currentScreen = "main" }
)
```

## Screen Components

### 1. Login Screen
- Email and password input fields with validation
- Remember me option
- Sign up navigation link
- Login button with loading state

```kotlin
@Composable
fun FoodXaLoginScreen(
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
)
```

### 2. Home Screen
- Categories section with horizontally scrollable items
- Popular foods section with grid layout
- Search functionality
- Navigation to food details and cart

```kotlin
@Composable
fun FoodXaHomeScreen(
    onNavigateToPopularFood: () -> Unit = {},
    onNavigateToMyCard: () -> Unit = {}
)
```

### 3. Cart Screen
- List of selected items
- Quantity adjustment
- Price calculation
- Checkout flow

### 4. Payment Screen
- Card information management
- Payment method selection
- Transaction confirmation

## State Management

The app uses Jetpack Compose's state management with:

1. **Local UI State**: `remember` and `mutableStateOf` for component-level state

```kotlin
var searchQuery by remember { mutableStateOf("") }
var passwordVisible by remember { mutableStateOf(false) }
```

2. **Persisted State**: `rememberSaveable` for state that survives configuration changes

```kotlin
var selectedTab by remember { mutableIntStateOf(0) }
```

3. **Derived State**: Used for calculations based on other state values

```kotlin
val statusBarAlpha by remember {
    derivedStateOf {
        (scrollState.value.toFloat() / 600f).coerceIn(0f, 1f)
    }
}
```

## Styling Patterns

### 1. Custom Colors
The app uses a consistent color scheme:

- Primary: Orange (`0xFFF97316`)
- Background: Dark (`0xFF1E1E1E`)
- Surface elements: Dark gray (`0xFF2A2A2A`)
- Text: White and gray variants

### 2. Custom Text Fields
Consistent styling for text input fields:

```kotlin
val textFieldColors = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color(0xFF2A2A2A),
    unfocusedContainerColor = Color(0xFF2A2A2A),
    disabledContainerColor = Color(0xFF2A2A2A),
    focusedBorderColor = Color(0xFFF97316),
    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    // ...additional color configurations
)
```

### 3. UI Components
Components use rounded corners and subtle shadows:

```kotlin
modifier = Modifier
    .clip(RoundedCornerShape(16.dp))
    .shadow(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        spotColor = Color(0xFFF97316).copy(alpha = 0.1f)
    )
```

## Implementation Guidelines

### 1. Data Models
Use data classes for model definitions:

```kotlin
data class FoodItem(
    val name: String,
    val price: Double,
    val rating: Float,
    val imageUrl: String,
    val reviews: Int
)

data class Category(
    val name: String,
    val icon: ImageVector
)
```

### 2. UI Component Structure
Follow this pattern for new UI components:

1. Top-level content container (Box, Column)
2. State declarations
3. UI component configuration (colors, focus requesters)
4. Component layout implementation

### 3. Image Loading
Use Coil for image loading with proper content scaling:

```kotlin
AsyncImage(
    model = food.imageUrl,
    contentDescription = food.name,
    modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    contentScale = ContentScale.Crop
)
```

### 4. Navigation
Implement navigation through callback functions:

```kotlin
fun YourScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToNextScreen: () -> Unit = {}
) {
    // Use these callbacks in buttons or other interactive elements
    Button(onClick = onNavigateToNextScreen) {
        Text("Next")
    }
}
```

---

This documentation provides a technical overview of the FoodXa app architecture and implementation details. It serves as a guide for understanding and extending the codebase.