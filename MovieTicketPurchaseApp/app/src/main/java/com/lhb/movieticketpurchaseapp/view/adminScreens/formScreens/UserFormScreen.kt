package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
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
import com.lhb.movieticketpurchaseapp.model.UserFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun UserFormScreen(
    idUpdate: String,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val userState = if (idUpdate != "") userViewModel.getInfomationAccountById(idUpdate).observeAsState(initial = null).value else null

    var formData by remember(userState) {
        mutableStateOf(userState?.toFormData() ?: UserFormData(role = 1))
    }

    var isNameErr by remember { mutableStateOf(false) }
    var isEmailErr by remember { mutableStateOf(false) }
    var isPhoneNumberError by remember { mutableStateOf(false) }
    var isPasswordErr by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Staff" else "Update Staff",
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
                onValueChange = { formData = formData.copy(name = it) },
                icon = Icons.Outlined.DriveFileRenameOutline,
                label = "Name",
                isContentError = isNameErr,
                errorMessage = "Name must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.phoneNumber,
                onValueChange = { formData = formData.copy(phoneNumber = it) },
                icon = Icons.Outlined.Phone,
                label = "Phone number",
                isContentError = isPhoneNumberError,
                errorMessage = "Phone number must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.email,
                onValueChange = { formData = formData.copy(email = it) },
                icon = Icons.Outlined.Email,
                label = "Email",
                isContentError = isEmailErr,
                errorMessage = "Email must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.password,
                onValueChange = { formData = formData.copy(password = it) },
                icon = Icons.Outlined.Password,
                label = "Password",
                isContentError = isEmailErr,
                errorMessage = "Password must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if(formData.name == ""){
                        isNameErr = true
                        return@CustomBigButton
                    }
                    if(formData.phoneNumber == ""){
                        isPhoneNumberError = true
                        return@CustomBigButton
                    }
                    if(formData.email == ""){
                        isEmailErr = true
                        return@CustomBigButton
                    }
                    if (formData.password == ""){
                        isPasswordErr = true
                        return@CustomBigButton
                    }
                    if(idUpdate == ""){
                        userViewModel.addUserAcc(formData){ success ->
                           coroutineScope.launch {
                               if(success){
                                   Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show()
                                   formData = UserFormData(role = 1)
                               }else{
                                   Toast.makeText(context, "Add failed", Toast.LENGTH_SHORT).show()
                               }
                           }
                        }
                    }else{
                        userViewModel.updateUser(idUpdate, formData){ success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show()
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