package com.lhb.movieticketpurchaseapp.view.userScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.model.BookingItemFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.ItemChooseFood
import com.lhb.movieticketpurchaseapp.view.components.TopBarChooseItem
import com.lhb.movieticketpurchaseapp.viewmodel.BookingItemViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.FoodDrinkViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ChooseFoodDrinkScreen(
    navController: NavController,
    foodDrinkViewModel: FoodDrinkViewModel,
    bookingItemViewModel: BookingItemViewModel
) {
    LaunchedEffect(Unit) {
        bookingItemViewModel.resetFormData()
    }
    var isShowButtonDelete by remember { mutableStateOf(false) }

    val foodDrinkListState = foodDrinkViewModel.foodDrinkList.observeAsState(initial = emptyList())
    val foodDrinkList = foodDrinkListState.value

    val bookingItemFormData = bookingItemViewModel.bookingItemFormDataList.value

    fun calculateTotalItemAmount(): Double {
        return bookingItemFormData.sumOf { item -> item.price * item.quantity }
    }
    val totalBuffetPrice = bookingItemFormData.sumOf { it.price * it.quantity }


    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarChooseItem(
                title = "Choose Foods & Drinks",
                isShowButtonDelete = isShowButtonDelete,
                onBackClick = { navController.popBackStack() },
                onClickDeleteAll = {
                    bookingItemViewModel.resetFormData()

                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .background(Color(0xff000000).copy(alpha = 0.5f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row {
                        Text(
                            text = "Total :",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = if(totalBuffetPrice == 0.0) "0 đ" else "$totalBuffetPrice đ",
                            color = Color(0xff6C47DB),
                            fontSize = 18.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Box(modifier = Modifier.width(150.dp)){
                        CustomBigButton(
                            title = "Next",
                            onClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(foodDrinkList.size) { index ->
                    val foodDrink = foodDrinkList[index]
                    ItemChooseFood(
                        foodDrink = foodDrink,
                        onClickToPlus = { _ ->
                            val existingItem = bookingItemFormData.find { it.itemId == foodDrink.id }
                            if (existingItem != null) {
                                bookingItemViewModel.updateBookingItemFormData(foodDrink.id, existingItem.quantity + 1)
                            } else {
                                bookingItemViewModel.addBookingItemFormData(
                                    BookingItemFormData(
                                        bookingId = "", // Gán bookingId phù hợp
                                        itemId = foodDrink.id,
                                        price = foodDrink.price,
                                        quantity = 1
                                    )
                                )
                            }
                            isShowButtonDelete = true
                            Log.d("TAG", "Item form data: " + bookingItemFormData)
                        },
                        onClickToMinus = { _ ->
                            val existingItem = bookingItemFormData.find { it.itemId == foodDrink.id }
                            if (existingItem != null) {
                                val updatedQuantity = existingItem.quantity - 1
                                if (updatedQuantity > 0) {
                                    bookingItemViewModel.updateBookingItemFormData(foodDrink.id, updatedQuantity)
                                } else {
                                    bookingItemViewModel.removeBookingItemFormData(existingItem)
                                }
                            }
                            if (bookingItemFormData.isEmpty()) {
                                isShowButtonDelete = false
                            }
                            Log.d("TAG", "Item form data: " + bookingItemFormData)
                        },
                        onItemInfor = { item -> }
                    )
                }
            }
        }
    }
}