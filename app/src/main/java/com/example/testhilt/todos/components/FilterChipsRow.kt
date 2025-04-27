package com.example.testhilt.todos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testhilt.todos.enums.TodoFilter

@Composable
fun FilterChipsRow(
    selectedFilter: TodoFilter,
    onFilterSelected: (TodoFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedFilter == TodoFilter.ALL,
            onClick = { onFilterSelected(TodoFilter.ALL) },
            label = { Text("All") },
            leadingIcon = {
                if (selectedFilter == TodoFilter.ALL) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        )

        FilterChip(
            selected = selectedFilter == TodoFilter.UPCOMING,
            onClick = { onFilterSelected(TodoFilter.UPCOMING) },
            label = { Text("Upcoming") },
            leadingIcon = {
                if (selectedFilter == TodoFilter.UPCOMING) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        )

        FilterChip(
            selected = selectedFilter == TodoFilter.RECURRING,
            onClick = { onFilterSelected(TodoFilter.RECURRING) },
            label = { Text("Recurring") },
            leadingIcon = {
                if (selectedFilter == TodoFilter.RECURRING) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        )
    }
}