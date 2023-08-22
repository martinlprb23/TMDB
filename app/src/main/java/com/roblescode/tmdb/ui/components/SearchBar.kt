package com.roblescode.tmdb.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.roblescode.tmdb.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    search: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        maxLines = 1,
        value = search,
        shape = CircleShape,
        onValueChange = onChange,
        label = { Text(text = stringResource(id = R.string.search))},
        placeholder = { Text(text = stringResource(id = R.string.search_by)) },
        trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}
