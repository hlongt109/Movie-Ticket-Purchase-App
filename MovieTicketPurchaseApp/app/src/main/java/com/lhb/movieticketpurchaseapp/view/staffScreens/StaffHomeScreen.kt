package com.lhb.movieticketpurchaseapp.view.staffScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.DropDownMenu
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.TicketViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel

@Composable
fun StaffHomeScreen(
    navController: NavController,
) {
    val isHaveNotice by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xff14111e),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.person),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.lg1),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Box {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xffffffff),
                                    shape = RoundedCornerShape(25.dp)
                                ),
                        ) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center),
                                tint = Color("#ffffff".toColorInt())
                            )
                        }
                        if (isHaveNotice) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 3.dp)
                                    .size(10.dp)
                                    .clip(
                                        RoundedCornerShape(5.dp)
                                    )
                                    .background(Color(0xff6C47DB))
                                    .align(Alignment.TopEnd),
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CustomBigButton(
                title = "Check Ticket",
                onClick = {
                    navController.navigate(Screens.CheckTicketScreen.route)
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            CustomBigButton(
                title = "Create Ticket",
                onClick = {
                   //
                }
            )
        }
    }
}
