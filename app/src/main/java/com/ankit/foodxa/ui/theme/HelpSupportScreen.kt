package com.ankit.foodxa.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    onNavigateBack: () -> Unit = {},
    onContactSupport: () -> Unit = {},
    onFAQItemClick: (FAQItem) -> Unit = {}
) {
    var expandedFAQ by remember { mutableStateOf<String?>(null) }
    
    val helpCategories = listOf(
        HelpCategory(
            title = "Order Issues",
            icon = Icons.Default.ShoppingCart,
            items = listOf(
                FAQItem(
                    id = "order-1",
                    question = "How do I track my order?",
                    answer = "You can track your order in real-time through the Order Tracking screen. Go to your profile and select 'Order History' to view all your orders and their current status."
                ),
                FAQItem(
                    id = "order-2",
                    question = "What if my order is delayed?",
                    answer = "If your order is delayed, you'll receive a notification with the updated delivery time. You can also contact our support team for immediate assistance."
                ),
                FAQItem(
                    id = "order-3",
                    question = "Can I cancel my order?",
                    answer = "You can cancel your order within 5 minutes of placing it. After that, please contact our support team for assistance."
                )
            )
        ),
        HelpCategory(
            title = "Payment & Billing",
            icon = Icons.Default.Payment,
            items = listOf(
                FAQItem(
                    id = "payment-1",
                    question = "What payment methods do you accept?",
                    answer = "We accept credit cards, debit cards, PayPal, and digital wallets like Apple Pay and Google Pay."
                ),
                FAQItem(
                    id = "payment-2",
                    question = "How do I get a refund?",
                    answer = "To request a refund, go to your order history, select the order, and tap 'Request Refund'. Our team will review your request within 24 hours."
                ),
                FAQItem(
                    id = "payment-3",
                    question = "Are there any hidden fees?",
                    answer = "No hidden fees! You'll see the delivery fee, service fee, and taxes clearly displayed before you place your order."
                )
            )
        ),
        HelpCategory(
            title = "Account & Profile",
            icon = Icons.Default.Person,
            items = listOf(
                FAQItem(
                    id = "account-1",
                    question = "How do I change my delivery address?",
                    answer = "Go to Settings > Addresses to manage your delivery addresses. You can add, edit, or remove addresses as needed."
                ),
                FAQItem(
                    id = "account-2",
                    question = "Can I change my phone number?",
                    answer = "Yes, you can update your phone number in Settings > Edit Profile. You'll need to verify the new number with a code."
                ),
                FAQItem(
                    id = "account-3",
                    question = "How do I delete my account?",
                    answer = "To delete your account, contact our support team. Please note that this action cannot be undone."
                )
            )
        ),
        HelpCategory(
            title = "App & Technical",
            icon = Icons.Default.Smartphone,
            items = listOf(
                FAQItem(
                    id = "tech-1",
                    question = "The app is not working properly",
                    answer = "Try closing and reopening the app, or restart your device. If the issue persists, please contact our support team."
                ),
                FAQItem(
                    id = "tech-2",
                    question = "How do I update the app?",
                    answer = "You can update the app through your device's app store (Google Play Store or App Store)."
                ),
                FAQItem(
                    id = "tech-3",
                    question = "I forgot my password",
                    answer = "Tap 'Forgot Password' on the login screen. You'll receive a reset link via email to create a new password."
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
                            text = "Help & Support",
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
                // Quick Contact Card
                item {
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
                                text = "Need immediate help?",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Our support team is available 24/7 to help you with any issues.",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, Color(0xFFF97316)),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color(0xFFF97316)
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Phone,
                                        contentDescription = "Call",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Call Us",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                
                                Button(
                                    onClick = onContactSupport,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFF97316)
                                    )
                                ) {
                                    Icon(
                                        Icons.Default.Chat,
                                        contentDescription = "Chat",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Live Chat",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                // FAQ Categories
                items(helpCategories) { category ->
                    HelpCategoryCard(
                        category = category,
                        expandedFAQ = expandedFAQ,
                        onFAQToggle = { faqId ->
                            expandedFAQ = if (expandedFAQ == faqId) null else faqId
                        },
                        onFAQClick = onFAQItemClick
                    )
                }

                // Contact Information
                item {
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
                                text = "Contact Information",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            ContactInfoItem(
                                icon = Icons.Default.Phone,
                                title = "Phone",
                                subtitle = "+1 (555) 123-4567",
                                action = "Call Now"
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            ContactInfoItem(
                                icon = Icons.Default.Email,
                                title = "Email",
                                subtitle = "support@foodxa.com",
                                action = "Send Email"
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            ContactInfoItem(
                                icon = Icons.Default.Schedule,
                                title = "Support Hours",
                                subtitle = "24/7 Available",
                                action = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HelpCategoryCard(
    category: HelpCategory,
    expandedFAQ: String?,
    onFAQToggle: (String) -> Unit,
    onFAQClick: (FAQItem) -> Unit
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.title,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = category.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            category.items.forEach { faq ->
                FAQItemCard(
                    faq = faq,
                    isExpanded = expandedFAQ == faq.id,
                    onToggle = { onFAQToggle(faq.id) },
                    onClick = { onFAQClick(faq) }
                )
                if (faq != category.items.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun FAQItemCard(
    faq: FAQItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onToggle,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF232323)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color(0xFFF97316)
                )
            }
            
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(
                        text = faq.answer,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    TextButton(
                        onClick = onClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFFF97316)
                        )
                    ) {
                        Text(
                            text = "Learn more",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContactInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    action: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFFF97316),
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        
        action?.let { actionText ->
            TextButton(
                onClick = { },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFFF97316)
                )
            ) {
                Text(
                    text = actionText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

data class HelpCategory(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val items: List<FAQItem>
)

data class FAQItem(
    val id: String,
    val question: String,
    val answer: String
)