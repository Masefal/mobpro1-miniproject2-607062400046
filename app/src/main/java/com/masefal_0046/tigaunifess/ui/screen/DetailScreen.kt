package com.masefal_0046.tigaunifess.ui.screen

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.masefal_0046.tigaunifess.util.ViewModelFactory
import com.masefal_0046.tigaunifess.R
import com.masefal_0046.tigaunifess.ui.theme.TigaUniFessTheme

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
    val showDialog = remember { mutableStateOf(false) }

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
                title = { Text(if (id == null) stringResource(R.string.add) else stringResource(R.string.edit)) },
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
                        val idKategori = when (selectedKategori) {
                            "Akademik" -> 1L
                            "Organisasi" -> 2L
                            "Kosan" -> 3L
                            else -> 4L
                        }
                        if (id == null) {
                            viewModel.insert(
                                konten = konten,
                                pengirim = pengirim,
                                idKategori = idKategori
                            )
                        } else {
                            viewModel.update(
                                id = id,
                                konten = konten,
                                pengirim = pengirim,
                                idKategori = idKategori
                            )
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog.value = true }
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
        if (id != null && showDialog.value) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog.value = false }
            ) {
                showDialog.value = false
                viewModel.delete(id)
                navController.popBackStack()
            }
        }
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = isi,
            onValueChange = onIsiChange,
            label = { Text(text = stringResource(R.string.isi_curhatan)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = pengirim,
            onValueChange = onPengirimChange,
            label = { Text(text = stringResource(R.string.nama_pengirim)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text(text = stringResource(R.string.pilih_kategori), style = MaterialTheme.typography.titleMedium)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small)
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
            Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.lainnya))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.hapus)) },
                onClick = { expanded = false; delete() }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    TigaUniFessTheme {
        DetailScreen(rememberNavController())
    }
}