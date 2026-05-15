package com.masefal_0046.tigaunifess.ui.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.masefal_0046.tigaunifess.model.Pesan
import com.masefal_0046.tigaunifess.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    val kategoriOptions = listOf("Akademik", "Organisasi", "Kosan", "Cinta")
    var selectedKategori by remember { mutableStateOf(kategoriOptions[0]) }

    var konten by remember { mutableStateOf("") }
    var pengirim by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getPesan(id) ?: return@LaunchedEffect
        konten = data.konten
        pengirim = data.pengirim
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text(if (id == null) "Tambah Sambat" else "Edit Sambat") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (konten.isBlank() || konten.length < 10) {
                            Toast.makeText(context, "Isi minimal 10 karakter!", Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) viewModel.insert(konten, pengirim)
                        else viewModel.update(id, konten, pengirim)

                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.Check, contentDescription = "Simpan")
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                    }
                }
            )
        }
    ) { padding ->
        FormPesan(
            isi = konten, onIsiChange = { konten = it },
            pengirim = pengirim, onPengirimChange = { pengirim = it },
            kategori = kategoriOptions,
            selectedKategori = selectedKategori,
            onKategoriChange = { selectedKategori = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun FormPesan(
    isi: String, onIsiChange: (String) -> Unit,
    pengirim: String, onPengirimChange: (String) -> Unit,
    kategori: List<String>,
    selectedKategori: String, onKategoriChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = isi,
            onValueChange = onIsiChange,
            label = { Text("Isi Sambatan") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = pengirim,
            onValueChange = onPengirimChange,
            label = { Text("Nama Pengirim (Opsional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(text = "Pilih Kategori:", style = MaterialTheme.typography.titleMedium)
        Column(
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
        ) {
            kategori.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (item == selectedKategori),
                            onClick = { onKategoriChange(item) }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = (item == selectedKategori), onClick = null)
                    Text(text = item, modifier = Modifier.padding(start = 16.dp))
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Hapus Permanen") },
                onClick = { expanded = false; delete() }
            )
        }
    }
}