package com.lhb.movieticketpurchaseapp.view

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.lhb.movieticketpurchaseapp.R
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.view.navigator.Screens
import com.lhb.movieticketpurchaseapp.viewmodel.LoginViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    var isEmailOrUsernameError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    ) {
        Image(
            painterResource(id = R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        // logo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .systemBarsPadding()
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.lg1),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                BasicTextField(
                    value = email,
                    onValueChange = {
                        isEmailOrUsernameError = false
                        email = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color("#242329".toColorInt()))
                        .padding(horizontal = 15.dp, vertical = 18.dp),
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    decorationBox = { innerTextField ->
                        if (email.isEmpty()) {
                            Text(
                                text = "E-mail",
                                color = Color("#5a595a".toColorInt()),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        innerTextField()
                    }
                )
                if (isEmailOrUsernameError) {
                    Text(
                        text = "Email or username must not be empty",
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = Color(0xffb1261c),
                        modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color("#242329".toColorInt())),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            isPasswordError = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color("#242329".toColorInt()))
                            .padding(horizontal = 15.dp, vertical = 18.dp)
                            .weight(1f),
                        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        keyboardActions = KeyboardActions(),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        decorationBox = { innerTextField ->
                            if (password.isEmpty()) {
                                Text(
                                    text = "Password",
                                    color = Color("#5a595a".toColorInt()),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            innerTextField()
                        }
                    )
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
                            tint = Color.White
                        )
                    }
                }
                if (isPasswordError) {
                    Text(
                        text = "Password must be at least 6 characters",
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = Color(0xffb1261c),
                        modifier = Modifier.padding(top = 5.dp, start = 15.dp, end = 15.dp)
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = {
                        if(email ==""){
                            isEmailOrUsernameError = true
                            return@Button
                        }
                        if(password == ""){
                            isPasswordError = true
                            return@Button
                        }
                        userViewModel.login(email,password){success ->
                            coroutineScope.launch {
                                if(success){
                                    Toast.makeText(context, "Login successfully", Toast.LENGTH_SHORT).show()
                                    val role = userViewModel.getUserRole()
                                    role.let {
                                        if (it == 0) {
                                            navController.navigate(Screens.AdminBottomTav.route){
                                                popUpTo(Screens.LoginScreen.route) { inclusive = true }
                                            }
                                        }
                                        if (it == 1) {
                                            // nhan vien
                                        }
                                        if (it == 2) {
                                            navController.navigate(Screens.UserBottomTav.route){
                                                popUpTo(Screens.LoginScreen.route) { inclusive = true }
                                            }
                                            Log.d("TAG", "userId: "+ userViewModel.getUserId())
                                            Log.d("TAG", "get role: "+ userViewModel.getUserRole())
                                        }
                                    }
                                    Log.d("TAG", "role: "+role)
                                }else{
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color("#312064".toColorInt()))
                ) {
                    Text(
                        text = "SIGN IN",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't you have an account?",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    TextButton(
                        onClick = { navController.navigate(Screens.SignUpScreen.route) }
                    ) {
                        Text(
                            text = "Sign Up",
                            color = Color("#6C47DB".toColorInt()),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {

}
