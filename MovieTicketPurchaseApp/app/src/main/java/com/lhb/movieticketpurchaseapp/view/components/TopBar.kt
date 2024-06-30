package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    isSearchActive: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit, //
    onSearchActiveChange: (Boolean) -> Unit, // turn on and off search
    onBackClick: () -> Unit = {},
    onSearchIconClick: () -> Unit = {},
    onMoreIconClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!isSearchActive) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                      onBackClick()
                    }
                ) {
                    Icon(
                        Icons.Outlined.ArrowBackIosNew, contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xffffffff)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    color = Color(0xffffffff),
                    fontSize = 18.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        onSearchActiveChange(true)
                        onSearchIconClick()
                    }
                ) {
                    Icon(
                        Icons.Outlined.Search, contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xffffffff)
                    )
                }

                IconButton(
                    onClick = { onMoreIconClick() }
                ) {
                    Icon(
                        Icons.Outlined.MoreVert, contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xffffffff)
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = {
                    Text(
                        text = "Search ...",
                        fontSize = 14.sp,
                        color = Color(0xffffffff),
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                    )
                },
                textStyle = TextStyle(
                    color = Color(0xffffffff),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { onSearchQueryChange("") }
                    ) {
                        Icon(
                            Icons.Outlined.Close, contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xffffffff)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xff6C47DB),
                    focusedBorderColor = Color(0xff6C47DB)
                ),
                shape = RoundedCornerShape(12.dp),
            )
            IconButton(
                onClick = {
                    onSearchActiveChange(false)
                    onSearchQueryChange("")
                }
            ) {
                Icon(
                    Icons.Outlined.Cancel, contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xffffffff)
                )
            }
        }
    }
}
