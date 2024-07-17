package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.TimeFrameFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.utils.showTimePickerDialog
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText3
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.TimeFrameViewModel
import kotlinx.coroutines.launch

@Composable
fun TimeFrameFormScreens(
    idUpdate: String,
    navController: NavController,
    timeFrameViewModel: TimeFrameViewModel
){
    val timeFramState = if(idUpdate != "") timeFrameViewModel.getTimeFrameById(idUpdate).observeAsState(initial = null).value else null

    var formData by remember(timeFramState) {
        mutableStateOf(timeFramState?.toFormData() ?: TimeFrameFormData())
    }

    var isStartTimeError by remember { mutableStateOf(false) }
    var isEndTimeError by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Time Frame" else "Update Time Frame",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CustomOutlineText3(
                value = formData.startTime,
                onValueChange = {formData = formData.copy(startTime = it)},
                icon = Icons.Outlined.AccessTime,
                label = "Start Time",
                isContentError = isStartTimeError,
                modifier = Modifier,
                onclick = {
                    showTimePickerDialog(context){time ->
                        formData = formData.copy(startTime = time)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlineText3(
                value = formData.endTime,
                onValueChange = {formData = formData.copy(endTime = it)},
                icon = Icons.Outlined.Timer,
                label = "End Time",
                isContentError = isEndTimeError,
                modifier = Modifier,
                onclick = {
                    showTimePickerDialog(context){time ->
                        formData = formData.copy(endTime = time)
                    }
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if(formData.startTime == ""){
                        isStartTimeError = true
                        return@CustomBigButton
                    }
                    if(formData.endTime == ""){
                        isEndTimeError = true
                        return@CustomBigButton
                    }
                    if(idUpdate == ""){
                        timeFrameViewModel.addTimeFrame(formData){success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Add time frame successfully", Toast.LENGTH_SHORT).show()
                                    formData = TimeFrameFormData()
                                }else{
                                    Toast.makeText(context, "Add time frame failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        timeFrameViewModel.updateTimeFrame(idUpdate,formData){success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Update time frame successfully", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }else{
                                    Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}