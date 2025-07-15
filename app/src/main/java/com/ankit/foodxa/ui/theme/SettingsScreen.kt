package com.ankit.foodxa.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onPrivacyPolicy: () -> Unit = {},
    onTermsOfService: () -> Unit = {},
    onHelpSupport: () -> Unit = {},
    onAbout: () -> Unit = {}
) {
    var pushNotifications by remember { mutableStateOf(true) }
    var emailNotifications by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(true) }
    var locationServices by remember { mutableStateOf(true) }
    var autoSave by remember { mutableStateOf(true) }

    val settingsSections = listOf(
        SettingsSection(
            title = "Account",
            items = listOf(
                SettingsOption(
                    title = "Edit Profile",
                    subtitle = "Update your personal information",
                    icon = Icons.Default.Person,
                    type = SettingsItemType.NAVIGATION,
                    onClick = onEditProfile
                ),
                SettingsOption(
                    title = "Payment Methods",
                    subtitle = "Manage your payment options",
                    icon = Icons.Default.Payment,
                    type = SettingsItemType.NAVIGATION,
                    onClick = { }
                ),
                SettingsOption(
                    title = "Addresses",
                    subtitle = "Manage your delivery addresses",
                    icon = Icons.Default.LocationOn,
                    type = SettingsItemType.NAVIGATION,
                    onClick = { }
                )
            )
        ),
        SettingsSection(
            title = "Preferences",
            items = listOf(
                SettingsOption(
                    title = "Push Notifications",
                    subtitle = "Receive order updates and offers",
                    icon = Icons.Default.Notifications,
                    type = SettingsItemType.SWITCH,
                    switchValue = pushNotifications,
                    onSwitchChange = { pushNotifications = it }
                ),
                SettingsOption(
                    title = "Email Notifications",
                    subtitle = "Get updates via email",
                    icon = Icons.Default.Email,
                    type = SettingsItemType.SWITCH,
                    switchValue = emailNotifications,
                    onSwitchChange = { emailNotifications = it }
                ),
                SettingsOption(
                    title = "Dark Mode",
                    subtitle = "Use dark theme",
                    icon = Icons.Default.DarkMode,
                    type = SettingsItemType.SWITCH,
                    switchValue = darkMode,
                    onSwitchChange = { darkMode = it }
                ),
                SettingsOption(
                    title = "Location Services",
                    subtitle = "Allow location access for better delivery",
                    icon = Icons.Default.LocationOn,
                    type = SettingsItemType.SWITCH,
                    switchValue = locationServices,
                    onSwitchChange = { locationServices = it }
                ),
                SettingsOption(
                    title = "Auto Save",
                    subtitle = "Automatically save your preferences",
                    icon = Icons.Default.Save,
                    type = SettingsItemType.SWITCH,
                    switchValue = autoSave,
                    onSwitchChange = { autoSave = it }
                )
            )
        ),
        SettingsSection(
            title = "Support",
            items = listOf(
                SettingsOption(
                    title = "Help & Support",
                    subtitle = "Get help with your orders",
                    icon = Icons.Default.Help,
                    type = SettingsItemType.NAVIGATION,
                    onClick = onHelpSupport
                ),
                SettingsOption(
                    title = "Privacy Policy",
                    subtitle = "Read our privacy policy",
                    icon = Icons.Default.PrivacyTip,
                    type = SettingsItemType.NAVIGATION,
                    onClick = onPrivacyPolicy
                ),
                SettingsOption(
                    title = "Terms of Service",
                    subtitle = "Read our terms of service",
                    icon = Icons.Default.Description,
                    type = SettingsItemType.NAVIGATION,
                    onClick = onTermsOfService
                ),
                SettingsOption(
                    title = "About",
                    subtitle = "App version and information",
                    icon = Icons.Default.Info,
                    type = SettingsItemType.NAVIGATION,
                    onClick = onAbout
                )
            )
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1E1E1E),
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                // Profile Section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF97316)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Dwayne Johnson",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "dwayne@example.com",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            IconButton(onClick = onEditProfile) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = Color(0xFFF97316)
                                )
                            }
                        }
                    }
                }

                // Settings Sections
                items(settingsSections) { section ->
                    SettingsSectionCard(section = section)
                }

                // Logout Button
                item {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF44336)
                        )
                    ) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Logout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // App Version
                item {
                    Text(
                        text = "FoodXa v1.0.0",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSectionCard(section: SettingsSection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = section.title,
                color = Color(0xFFF97316),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            section.items.forEachIndexed { index, item ->
                SettingsItemRow(
                    item = item,
                    isLast = index == section.items.size - 1
                )
                if (index < section.items.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SettingsItemRow(
    item: SettingsOption,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = Color(0xFFF97316),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item.subtitle,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        when (item.type) {
            SettingsItemType.SWITCH -> {
                Switch(
                    checked = item.switchValue ?: false,
                    onCheckedChange = item.onSwitchChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFF97316),
                        checkedTrackColor = Color(0xFFF97316).copy(alpha = 0.5f),
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
            }

            SettingsItemType.NAVIGATION -> {
                IconButton(onClick = item.onClick ?: {}) {
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Navigate",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

enum class SettingsItemType {
    SWITCH,
    NAVIGATION
}

data class SettingsSection(
    val title: String,
    val items: List<SettingsOption>
)

data class SettingsOption(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val type: SettingsItemType,
    val switchValue: Boolean? = null,
    val onSwitchChange: ((Boolean) -> Unit)? = null,
    val onClick: (() -> Unit)? = null
)