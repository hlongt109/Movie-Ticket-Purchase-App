package com.lhb.movieticketpurchaseapp.view

import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import android.os.Handler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lhb.movieticketpurchaseapp.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.lhb.movieticketpurchaseapp.view.navigator.Screens

@Composable
fun WelcomeScreen(navController: NavController) {
    val handle = Handler(Looper.getMainLooper())
    var startAnimation by remember { mutableStateOf(false) }
    var startAnimation_button by remember { mutableStateOf(false) }
    //
    val logoImage: Painter = painterResource(id = R.drawable.lg1)

    LaunchedEffect(Unit) {
        handle.postDelayed({
            startAnimation = true
        }, 500)

        handle.postDelayed({
            startAnimation_button = true
        }, 1500)
    }
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
    val buttonAnimation by animateFloatAsState(
        targetValue = if (startAnimation_button) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )
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
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = logoImage,
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .alpha(alphaAnimation)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp)
                    .alpha(buttonAnimation)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpTo(Screens.WelcomeScreen.route) { inclusive = true }
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    )
                    Text(
                        text = "or",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(Screens.SignUpScreen.route) {
                            popUpTo(Screens.WelcomeScreen.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color("#312064".toColorInt()))
                ) {
                    Text(
                        text = "SIGN UP",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDividerWithText() {

}