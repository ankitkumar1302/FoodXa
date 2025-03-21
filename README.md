# FoodXa - Modern Food Delivery App

FoodXa is a sleek, modern food delivery application built with Jetpack Compose that showcases best practices in Android development. The app features a beautiful dark-themed UI design with orange accents, intuitive navigation, and a comprehensive set of features for food ordering.

## 📱 App Overview

FoodXa is a complete solution for food ordering with:

- **User Authentication**: Login and registration flows with field validation
- **Food Discovery**: Browse food by categories, search, and view popular items
- **Shopping Cart**: Add items, adjust quantities, and checkout
- **Payment Processing**: Card management and transaction history
- **User Profiles**: Personal information management

## 🧩 Key Features

### Intuitive User Interface
- Dark theme with vibrant orange accents
- Smooth animations and transitions
- Responsive layouts that adapt to different screen sizes
- Modern Material Design 3 components

### User Experience
- Simple authentication flow
- Categorized food browsing
- Food ratings and reviews
- Favorites system for quick reordering
- Order tracking and history

### Technical Implementation
- 100% Jetpack Compose UI
- Component-based architecture for code reuse
- Efficient state management
- Focus on performance and responsiveness

## 🔍 Project Structure

The project follows a logical structure organized by features:

```
com.ankit.foodxa/
├── MainActivity.kt           # App entry point and navigation controller
└── ui/
    └── theme/
        ├── AllTransactionsScreen.kt    # Transaction history
        ├── CartScreen.kt               # Shopping cart
        ├── Color.kt                    # Color definitions
        ├── FoodXaHomeScreen.kt         # Main homepage
        ├── FoodXaLoginScreen.kt        # User login
        ├── FoodXaSignUpScreen.kt       # User registration
        ├── MyCardScreen.kt             # Payment methods
        ├── PopularFoodScreen.kt        # Featured food items
        ├── ProfileScreen.kt            # User profile
        ├── Theme.kt                    # Theme configuration
        └── Type.kt                     # Typography definitions
```

## 📋 Implementation Details

### Navigation System
The app implements a custom navigation system with two main flows:
1. **Authentication flow** (login/signup)
2. **Main app flow** with tabbed navigation for:
   - Home feed
   - Search/Explore
   - Shopping cart
   - User profile

```kotlin
// Navigation handling in MainActivity
when (currentScreen) {
    "auth" -> {
        // Show login or signup screens
    }
    "main" -> {
        // Show main app content with bottom navigation
    }
}
```

### UI Components
FoodXa uses custom composable components for consistent design:

- **Food Cards**: Display food items with images, ratings, and prices
- **Category Pills**: Horizontally scrollable category selectors
- **Custom Text Fields**: Styled input fields for user data
- **Bottom Navigation**: Tab-based navigation with animation

### Data Structure
The app uses clean data models for representing different entities:

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

### Styling Approach
FoodXa uses a consistent styling approach with:

- A dark color scheme with orange accent (`0xFFF97316`)
- Rounded corners (16.dp) for most components
- Subtle shadows for depth and hierarchy
- Consistent typography and spacing

## 🚀 Getting Started

### Prerequisites
- Android Studio Flamingo (2023.2.1) or newer
- JDK 11 or higher
- Android SDK 35

### Running the App
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run on an emulator or physical device

## 📸 App Screenshots

[Screenshots will be added soon]

## 👥 Contributing

Contributions to FoodXa are welcome! Here are some areas where help is appreciated:

- Enhanced animations and transitions
- Improved accessibility features
- Additional food categories and filtering options
- Unit and integration tests

## 👨‍💻 Author

**Ankit Kumar**
- GitHub: [ankitkumar1302](https://github.com/ankitkumar1302)

---

## ✨ App Highlights

### Beautiful Design
The app features a stunning dark theme with careful attention to visual hierarchy, animations, and user experience. The orange accent color provides vibrant contrast against the dark background.

### Seamless Navigation
The navigation system is designed to be intuitive with smooth transitions between screens. Back navigation is handled consistently throughout the app.

### Attention to Detail
Small touches like loading animations, input field validation, and status bar handling provide a polished user experience.

---

FoodXa demonstrates modern Android development practices while providing a complete solution for food delivery services.