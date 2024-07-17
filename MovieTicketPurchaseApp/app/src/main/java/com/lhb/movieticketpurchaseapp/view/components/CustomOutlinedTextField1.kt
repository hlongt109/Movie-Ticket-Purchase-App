package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField1(
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    label: String,
    isContentError: Boolean,
    modifier: Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = "$label :",
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
                imageVector = icon,
                contentDescription = "Leading Icon",
                tint = Color.White
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onValueChange("")
                }
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
        isError = isContentError
    )
}