package com.students.salonapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.students.salonapp.data.models.Service
import com.students.salonapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(
    onBack: () -> Unit,
    onServiceClick: (Service) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showFilters by remember { mutableStateOf(false) }
    
    val services = listOf(
        Service(
            id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22",
            name = "Балаяж",
            description = "Профессиональное окрашивание волос балаяж",
            duration = 120,
            price = 199.0,
            image_url = "https://avatars.mds.yandex.net/get-altay/5456749/2a0000017cdacd6564a4d79571ed2b1519ac/XXXL",
            category_id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22"
        ),
        Service(
            id = "e08679a8-dd3f-4bb2-a019-a45c158dfac8",
            name = "Окрашивание",
            description = "Полное окрашивание головы высококачественными красителями",
            duration = 90,
            price = 99.0,
            image_url = "https://avatars.mds.yandex.net/i?id=3d2c8328ded5f3e9d440e688dedbcc2ad152fd5a-5222573-images-thumbs&n=13",
            category_id = "e08679a8-dd3f-4bb2-a019-a45c158dfac8"
        )
    )
    updateTransition(showFilters, label = "filter")
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            salonPinkPrimaryContainer.copy(alpha = 0.1f),
                            salonSurfaceLight
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Услуги",
                            style = MaterialTheme.typography.headlineSmall,
                            color = salonPinkOnPrimaryContainer
                        )
                        Text(
                            text = "${services.size} услуг",
                            style = MaterialTheme.typography.bodyMedium,
                            color = salonPinkOnPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = salonPinkOnPrimaryContainer
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Фильтры",
                            tint = salonPinkOnPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = salonPinkPrimaryContainer,
                    navigationIconContentColor = salonPinkOnPrimaryContainer,
                    actionIconContentColor = salonPinkOnPrimaryContainer
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Все", "Стрижки", "Окрашивание", "Укладка", "Маникюр").forEach { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = showFilters,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Фильтры",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Цена") },
                                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Длительность") },
                                leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) }
                            )
                            FilterChip(
                                selected = false,
                                onClick = { },
                                label = { Text("Популярность") },
                                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    items = services,
                    key = { it.id }
                ) { service ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp))
                                .clickable { onServiceClick(service) },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = service.image_url,
                                    contentDescription = service.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = service.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = service.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${service.price} ₽",
                                                style = MaterialTheme.typography.titleMedium,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "${service.duration} мин",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Button(
                                            onClick = { onServiceClick(service) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = salonPinkPrimaryContainer,
                                                contentColor = salonPinkOnPrimaryContainer
                                            ),
                                            shape = RoundedCornerShape(24.dp)
                                        ) {
                                            Text("Выбрать")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}