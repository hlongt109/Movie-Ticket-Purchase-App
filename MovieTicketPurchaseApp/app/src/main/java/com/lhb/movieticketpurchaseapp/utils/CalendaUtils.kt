package com.lhb.movieticketpurchaseapp.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import java.io.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun showTimePickerDialog(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(context, { _, selectedHour: Int, selectedMinute: Int ->
        onTimeSelected(String.format("%02d:%02d", selectedHour, selectedMinute))
    }, hour, minute, true).show()
}

@Composable
fun DatePickerRow(
    context: Context,
    initialDate: String,
    onDateSelected: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    val currentDate = remember { Calendar.getInstance().time }
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val formattedCurrentDate = dateFormat.format(currentDate)

    // Update selectedDate with the initial date or current date
    if (selectedDate.isEmpty()) {
        selectedDate = formattedCurrentDate
        onDateSelected(formattedCurrentDate)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Select Show Time Date:",
            color = Color.White
        )
        Text(
            text = AnnotatedString(selectedDate),
            color = Color.White,
            modifier = Modifier
                .clickable {
                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(year, month, dayOfMonth)
                            val newDate = dateFormat.format(selectedCalendar.time)
                            selectedDate = newDate
                            onDateSelected(newDate)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                    datePickerDialog.show()
                }
                .padding(16.dp)
        )
    }
}

@Composable
fun CustomDatePickerRow(
    initialDate: String,
    onDateSelected: (String) -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("EEE dd/MM/yyyy", Locale.getDefault()) }
    val calendar = remember { Calendar.getInstance() }
    val currentDate = remember { calendar.time }
    val formattedCurrentDate = dateFormat.format(currentDate)

    var selectedDate by remember { mutableStateOf(initialDate.ifEmpty { formattedCurrentDate }) }

    // Generate list of dates
    val dates = remember {
        List(30) { index ->
            calendar.time = currentDate
            calendar.add(Calendar.DAY_OF_YEAR, index)
            dateFormat.format(calendar.time)
        }
    }

    // Update selectedDate with the initial date or current date
//    if (initialDate.isEmpty()) {
//        selectedDate = formattedCurrentDate
//        onDateSelected(formattedCurrentDate)
//    }
    LaunchedEffect(initialDate) {
        if (initialDate.isEmpty()) {
            selectedDate = formattedCurrentDate
            onDateSelected(formattedCurrentDate)
        }
    }
    Text(
        text = "Select Show Time Date:",
        color = Color(0xffffffff),
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dates) { date ->
            Text(
                text = date,
                color = if (date == selectedDate) Color(0xff6C47DB) else Color(0xff14111e),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xffffffff))
                    .border(
                        width = 1.dp,
                        color = if (date == selectedDate) Color(0xff6C47DB) else Color(0xff14111e),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable {
                        selectedDate = date
                        onDateSelected(date)
                    }

            )
        }
    }
}