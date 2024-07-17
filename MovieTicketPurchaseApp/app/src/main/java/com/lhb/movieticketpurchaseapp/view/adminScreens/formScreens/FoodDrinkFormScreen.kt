package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.lhb.movieticketpurchaseapp.model.FoodAndDrinkFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.utils.createFileByUri
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText2
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import kotlinx.coroutines.launch

@Composable
fun FoodDrinkFormScreens(
    idUpdate: String,
    foodDrinkViewModel: FoodDrinkViewModel,
    navController: NavController
){
    val foodDrinkState = if(idUpdate != "") foodDrinkViewModel.getFoodDrinkById(idUpdate).observeAsState(initial = null).value else null
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var formData by remember(foodDrinkState) {
        mutableStateOf(foodDrinkState?.toFormData() ?: FoodAndDrinkFormData())
    }

    val imageLaucher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
       it?.let {
           val fileImage = createFileByUri(context, it)
           fileImage.let { formData = formData.copy(image = fileImage) }
       }
    }

    var isNameError by remember { mutableStateOf(false) }
    var isPriceError by remember { mutableStateOf(false) }
    var isImageError by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Food & Drink" else "Update Food & Drink",
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
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Food or Drink photo :",
                    color = Color(0xffffffff),
                    fontSize = 14.sp, fontFamily = Inter,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .aspectRatio(1 / 1f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = if (isImageError) Color(0xffb1261c) else Color(0xff6C47DB),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            imageLaucher.launch("image/*")
                            isImageError = false
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (formData.image.isNotEmpty()) {
                        Image(
                            painter = rememberImagePainter(
                                ImageRequest.Builder(context)
                                    .data(formData.image)
                                    .build()
                            ),
                            contentDescription = null,
                            modifier = Modifier.aspectRatio(1/1f),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            Icons.Outlined.AddCircleOutline,
                            contentDescription = "",
                            tint = Color(0xffffffff),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField(
                value = formData.name,
                onValueChange = {formData = formData.copy(name = it)},
                icon = Icons.Outlined.DriveFileRenameOutline,
                label = "Name",
                isContentError = isNameError,
                errorMessage = "Name must not be empty",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlineText2(
                value = if(formData.price == 0.0)"" else formData.price.toString(),
                onValueChange = {formData = formData.copy(price = it.toDoubleOrNull() ?: 0.0)},
                icon = Icons.Outlined.AttachMoney,
                label = "Price",
                isContentError = isPriceError,
                modifier = Modifier,
                placeholder = "vnd",
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
                    if(formData.price == 0.0){
                        isPriceError = true
                        return@CustomBigButton
                    }
                    if(formData.image == ""){
                        isImageError = true
                        return@CustomBigButton
                    }
                    if(idUpdate == ""){
                        foodDrinkViewModel.addFoodDrink(formData){success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show()
                                    formData = FoodAndDrinkFormData()
                                }else{
                                    Toast.makeText(context, "Add failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }else{
                        foodDrinkViewModel.updateFoodDrinks(idUpdate,formData){success ->
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