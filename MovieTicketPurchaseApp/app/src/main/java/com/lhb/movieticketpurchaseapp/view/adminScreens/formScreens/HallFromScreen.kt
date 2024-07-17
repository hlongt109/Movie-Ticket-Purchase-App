package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Theaters
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
import com.lhb.movieticketpurchaseapp.model.CinemaHallFormData
import com.lhb.movieticketpurchaseapp.model.toCinemaHallFormData
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText2
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField1
import com.lhb.movieticketpurchaseapp.view.components.CustomTextFieldOnlyRead
import com.lhb.movieticketpurchaseapp.view.components.CustomTheaterDropDown
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.CinemaHallViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import kotlinx.coroutines.launch

@Composable
fun HallFormScreen(
    idUpdate: String,
    theaterId: String,
    theaterName: String,
    cinemaHallViewModel: CinemaHallViewModel,
    theaterViewModel: TheaterViewModel,
    navController: NavController,
) {
    val hallState = if (idUpdate != "empty" && idUpdate != "") cinemaHallViewModel.getCinemaHallById(idUpdate)
        .observeAsState(initial = null).value else null

    val theaterState by theaterViewModel.theaterList.observeAsState(initial = emptyList())

    val initialTheaterId = if (theaterId == "empty" && theaterState.isNotEmpty()) {
        theaterState[0].id
    } else {
        theaterId
    }
    var selectedTheaterId by remember { mutableStateOf(initialTheaterId) }

    var formData by remember(hallState) {
        mutableStateOf(hallState?.toCinemaHallFormData() ?: CinemaHallFormData(theaterId = initialTheaterId))
    }

    var isNameError by remember { mutableStateOf(false) }
    var isCapacityError by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "empty") "Create Hall" else "Update Hall",
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
            if(theaterId != "empty" && theaterName != "empty"){
                CustomTextFieldOnlyRead(
                    value = if(theaterName == "") "None" else theaterName,
                    icon = Icons.Outlined.Theaters,
                    label = "Theater",
                    modifier = Modifier
                )
            }else{
                CustomTheaterDropDown(
                    listTheater = theaterState,
                    initialSelectedItemId = selectedTheaterId,
                    onItemSelected = {selectedId ->
                        selectedTheaterId = selectedId
                        formData = formData.copy(theaterId = selectedId)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField1(
                value = formData.name,
                onValueChange = {
                    formData = formData.copy(name = it)
                    isNameError = false
                },
                icon = Icons.Outlined.DriveFileRenameOutline,
                label = "Hall Name",
                isContentError = isNameError,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlineText2(
                value = if(formData.capacity == 0) "" else formData.capacity.toString(),
                onValueChange = { value ->
                    formData = formData.copy(capacity = value.toIntOrNull() ?: 0)
                    isCapacityError = false
                },
                icon = Icons.Outlined.GridOn,
                label = "Capacity",
                isContentError = isCapacityError,
                modifier = Modifier,
                placeholder = "seat number",
                onclick = {}
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if(formData.name == ""){
                        isNameError = true
                        return@CustomBigButton
                    }
                    if(formData.capacity == 0){
                        isCapacityError = true
                        return@CustomBigButton
                    }
                    if(idUpdate == "empty" && idUpdate != ""){
                        Log.d("TAG", "HallFormScreen: "+formData)
                        cinemaHallViewModel.addCinemaHall(formData){result ->
                            coroutineScope.launch {
                                if(result){
                                    Toast.makeText(context, "Add Hall Successfully", Toast.LENGTH_SHORT).show()
                                    formData = CinemaHallFormData(theaterId = initialTheaterId)
                                    selectedTheaterId = theaterState[0].id
                                }else{
                                    Toast.makeText(context, "Add Hall Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        Log.d("TAG", "HallFormScreen: "+formData)
                        cinemaHallViewModel.updateCinemaHall(idUpdate,formData){result ->
                            coroutineScope.launch {
                                if(result){
                                    Toast.makeText(context, "Update hall successfully", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }else{
                                    Toast.makeText(context, "Update hall failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}