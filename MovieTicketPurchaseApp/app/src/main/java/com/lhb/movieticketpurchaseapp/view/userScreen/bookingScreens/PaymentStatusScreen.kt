package com.lhb.movieticketpurchaseapp.view.userScreen.bookingScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

//@Preview
@Composable
fun PaymentStatusScreen(
    navController: NavController,
    paymentStatus: Boolean
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .padding(top = 70.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Image(
                painterResource(id = R.drawable.bg_dom),
                contentDescription =null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Image(
                painter = painterResource(id = if(paymentStatus)R.drawable.checked else R.drawable.exit),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
        Text(
            text = if(paymentStatus) "Payment Successful" else "Payment Failed",
            fontSize = 26.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if(paymentStatus) "We have sent a copy of your ticket to your e-mail address. You can check your ticket in the My Tickets section on the homepage." else "Your ticket purchase could not be processed because there was a problem with the payment process. Try to buy a ticket again by pressing the try again button.",
            fontSize = 14.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(70.dp))
        CustomBigButton(title = "Back to Home",
            onClick = {
                navController.navigate(Screens.UserBottomTav.route){
                    popUpTo(Screens.BookingScreen.route)
                }
            }
        )
    }
}