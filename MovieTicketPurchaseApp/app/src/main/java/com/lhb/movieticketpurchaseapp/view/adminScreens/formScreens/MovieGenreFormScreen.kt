package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.FAButton
import com.lhb.movieticketpurchaseapp.view.components.TopBar
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieGenreFormScreen(
    idUpdate: String,
    navController: NavController,
    movieGenreViewModel: MovieGenreViewModel
){
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if(idUpdate == "") "Create Movie Genre" else "Update Movie Genre",
                onBackClick = {navController.popBackStack()}
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
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                label = {
                    Text(text = "Name :",fontSize = 14.sp, color = Color(0xffffffff),fontFamily = Inter, fontWeight = FontWeight.Medium,)
                },
                textStyle = TextStyle(
                    color = Color(0xffffffff),
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DriveFileRenameOutline,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {

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
            )
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(title = "SAVE", onClick = {  })
        }
    }
}