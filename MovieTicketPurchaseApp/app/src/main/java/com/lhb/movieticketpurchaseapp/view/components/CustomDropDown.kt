package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.LibraryAddCheck
import androidx.compose.material.icons.outlined.TypeSpecimen
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
    listItems: List<String>,
    initialSelectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(if (initialSelectedItem == "") listItems[0] else initialSelectedItem) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xff14111e)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = if(initialSelectedItem == "") selectedText else initialSelectedItem,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                label = {
                    Text(
                        text = "Genre :",
                        fontSize = 14.sp,
                        color = Color(0xffffffff),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                    )
                },
                textStyle = TextStyle(fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Medium, color = Color.White),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .background(Color(0xff14111e)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xff14111e),
                    unfocusedBorderColor = Color(0xff6C47DB),
                    focusedBorderColor = Color(0xff6C47DB),
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.TypeSpecimen,
                        contentDescription = "Leading Icon",
                        tint = Color.White
                    )
                },
                shape = RoundedCornerShape(12.dp),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xff14111e))
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .heightIn(max = 200.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xffffffff).copy(alpha = 0.7f),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        listItems.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = { Text(text = s, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Medium, color = Color.White) },
                                onClick = {
                                    selectedText = listItems[index]
                                    onItemSelected(listItems[index])
                                    expanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xff14111e)),
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        }
    }
}
