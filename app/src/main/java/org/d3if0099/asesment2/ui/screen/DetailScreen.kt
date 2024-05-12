package org.d3if0099.asesment2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0099.asesment2.database.PenghuniDb
import org.d3if0099.asesment2.ui.theme.Asesment2Theme
import org.d3if0099.asesment2.R
import org.d3if0099.asesment2.util.ViewModelFactory

const val KEY_ID_PENGHUNI = "idPenghuni"

val items = listOf("Lantai-1", "Lantai-2", "Lantai-3", "Lantai-4")
val hijau = Color(0xFF99F18E)
val putih = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = PenghuniDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var nokamar by remember { mutableStateOf("") }
    var lantai by remember { mutableStateOf(items[0]) }
    var gedung by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getMahasiswa(id) ?: return@LaunchedEffect
        nama = data.nama
        nokamar = data.nokamar
        lantai = data.lantai
        gedung = data.gedung
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.kembali
                            ),
                            tint = putih
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_penghuni))
                    else
                        Text(text = stringResource(id = R.string.edit_penghuni))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = hijau,
                    titleContentColor = putih
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || nokamar == "" || lantai == "" || gedung == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama, nokamar, lantai, gedung)
                        } else {
                            viewModel.update(id, nama, nokamar, lantai, gedung)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(
                                id = R.string.simpan
                            ),
                            tint = putih
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormMahasiswa(
            modifier = Modifier.padding(padding),
            nama = nama,
            onNamaChange = { nama = it },
            nokamar = nokamar,
            onNokamarChange = { nokamar = it },
            lantai = lantai,
            onLantaiChange = { lantai = it },
            gedung = gedung,
            onGedungChange = { gedung = it }
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.hapus)) },
                onClick = {
                    expanded = false
                    delete()
                },
            )
        }
    }
}


@Composable
fun FormMahasiswa(
    modifier: Modifier,
    nama: String, onNamaChange: (String) -> Unit,
    nokamar: String, onNokamarChange: (String) -> Unit,
    lantai: String, onLantaiChange: (String) -> Unit,
    gedung: String, onGedungChange: (String) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama,
            onValueChange = { onNamaChange(it) },
            label = { Text(text = stringResource(id = R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nokamar,
            onValueChange = { onNokamarChange(it) },
            label = { Text(text = stringResource(id = R.string.nokamar)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gedung,
            onValueChange = { onGedungChange(it) },
            label = { Text(text = stringResource(id = R.string.gedung)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            items.forEach { text ->
                RadioOptions(
                    label = text,
                    isSelected = lantai == text,
                    modifier = Modifier
                        .selectable(
                            selected = lantai == text,
                            onClick = { onLantaiChange(text) },
                            role = Role.RadioButton
                        )
                        .padding(8.dp), onLantaiChange
                )
            }
        }
    }
}


@Composable
fun RadioOptions(
    label: String,
    isSelected: Boolean,
    modifier: Modifier,
    onKelasChange: (String) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = { onKelasChange(label) })
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DetailScreenPreview() {
    Asesment2Theme {
        DetailScreen(rememberNavController())
    }
}