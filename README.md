# FoodXa

FoodXa is a modern Android food delivery application built with Jetpack Compose that provides a seamless food ordering experience. This codebase demonstrates best practices in Android development using Material 3 design principles.

## 🌟 Features

- **Modern UI/UX**: Built entirely with Jetpack Compose with beautiful animations and transitions
- **Material Design 3**: Implements the latest Material Design guidelines
- **Form Validation**: Smart validation for user inputs with helpful error messages
- **State Management**: Efficient state management using Compose state
- **Responsive Layout**: Adapts to different screen sizes and orientations

## 🔑 Key Components

### UI Modules

- **Login Screen**: A modern, responsive login interface with robust validation
  - Located in `app/src/main/java/com/ankit/foodxa/ui/theme/sampi.kt`
  - Features email validation, password protection, and error handling
  - Implements loading states and success feedback

- **Theme Configuration**: Custom theme with carefully selected colors and typography
  - Consistent color palette with primary, secondary, and accent colors
  - Dark mode support with appropriate contrast ratios

### Code Organization

The codebase follows modern Android development patterns:

- **Composables**: UI components built with Jetpack Compose
- **State Handling**: Uses `rememberSaveable` for state preservation across recompositions
- **Coroutines**: Asynchronous operations handled via Kotlin coroutines
- **Error Handling**: Comprehensive error states with user-friendly messages

## 📋 Understanding the Structure

```
app/
├── src/main/
│   ├── java/com/ankit/foodxa/
│   │   ├── ui/
│   │   │   ├── theme/       # App theme definitions and login screen
│   │   │   └── ...          # Other UI components
│   │   └── MainActivity.kt  # Entry point
│   └── res/                 # Resources
└── build.gradle            # App-level build configuration
```

## 🛠️ Tech Stack

- **Kotlin**: 100% Kotlin codebase
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material 3**: Latest Material Design components
- **Coroutines**: For asynchronous programming

## 📝 Code Patterns & Best Practices

The codebase demonstrates several important patterns:

1. **Explicit Lambda Labels**: Uses explicit labels for lambda returns to improve code clarity
   ```kotlin
   val handleLogin = suspend login@ {
       // Implementation with explicit returns
       return@login
   }
   ```

2. **State Hoisting**: Keeps state at appropriate levels and passes down only necessary information
   ```kotlin
   var isLoggedIn by rememberSaveable { mutableStateOf(false) }
   ```

3. **Modular UI**: Breaks down complex interfaces into manageable components
   ```kotlin
   @Composable
   fun WelcomeMessage(name: String) {
       // Reusable component implementation
   }
   ```

4. **Consistent Styling**: Uses theme constants for colors, shapes, and typography
   ```kotlin
   val PrimaryColor = Color(0xFF3B82F6)
   val SecondaryColor = Color(0xFF8B5CF6)
   ```

## 🚀 Getting Started

To explore and understand this codebase:

1. Open the project in Android Studio
2. Examine the main UI components in `app/src/main/java/com/ankit/foodxa/ui/theme/sampi.kt`
3. Note how the login form validates user input and provides feedback
4. Observe the state management patterns and Compose UI construction

## 💡 Areas of Focus

When reviewing this codebase, pay special attention to:

1. The form validation logic in the `handleLogin` function
2. How loading and error states are managed
3. The implementation of modular UI components
4. The use of Material 3 design principles

## 👨‍💻 Author

**Ankit Kumar**
- GitHub: [ankitkumar1302](https://github.com/ankitkumar1302)

---

Happy coding! 🚀