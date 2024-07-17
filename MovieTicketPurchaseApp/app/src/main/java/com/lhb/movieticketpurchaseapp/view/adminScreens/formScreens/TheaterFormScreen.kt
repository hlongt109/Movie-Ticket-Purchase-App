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
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.LocationOn
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
import com.lhb.movieticketpurchaseapp.model.TheaterFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.TheaterViewModel
import kotlinx.coroutines.launch

@Composable
fun TheaterFormScreens(
    idUpdate: String,
    navController: NavController,
    theaterViewModel: TheaterViewModel
) {
    Log.d("TAG", "id Update: "+idUpdate)
    val theaterState = if (idUpdate != "") theaterViewModel.getTheaterById(idUpdate).observeAsState(initial = null).value else null

    var formData by remember(theaterState) {
        mutableStateOf(theaterState?.toFormData() ?: TheaterFormData())
    }

    var isNameError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }
    var isContactError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Theater" else "Update Theater",
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
            CustomOutlinedTextField(
                value = formData.name,
                onValueChange = {
                    formData = formData.copy(name = it)
                },
                icon = Icons.Outlined.HomeWork,
                label = "Name ",
                isContentError = isNameError,
                errorMessage = "Cinema name must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.location,
                onValueChange = { formData = formData.copy(location = it) },
                icon = Icons.Outlined.LocationOn,
                label = "Location ",
                isContentError = isNameError,
                errorMessage = "Location must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.contact,
                onValueChange = {
                    formData = formData.copy(contact = it)
                },
                icon = Icons.Outlined.Call,
                label = "Contract ",
                isContentError = isNameError,
                errorMessage = "Contract must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if(formData.name == ""){
                        isNameError = true
                        return@CustomBigButton
                    }
                    if(formData.location == ""){
                        isLocationError = true
                        return@CustomBigButton
                    }
                    if(formData.contact == ""){
                        isContactError = true
                        return@CustomBigButton
                    }
                    //
                    if(idUpdate == ""){
                        theaterViewModel.addNewTheater(formData){ success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Add theater successfully", Toast.LENGTH_SHORT).show()
                                    formData = TheaterFormData()
                                }else{
                                    Toast.makeText(context, "Add theater failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        theaterViewModel.updateTheater(idUpdate,formData){ isSuccess ->
                            coroutineScope.launch {
                                if (isSuccess){
                                    Toast.makeText(context, "Update theater successfully", Toast.LENGTH_SHORT).show()
                                    formData = TheaterFormData()
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