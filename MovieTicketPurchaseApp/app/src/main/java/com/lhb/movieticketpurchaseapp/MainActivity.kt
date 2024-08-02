package com.lhb.movieticketpurchaseapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.lhb.movieticketpurchaseapp.network.UserService
import com.lhb.movieticketpurchaseapp.ui.theme.MovieTicketPurchaseAppTheme
import com.lhb.movieticketpurchaseapp.view.navigator.ScreenNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.navigationBarColor = getColor(R.color.black)
        window.statusBarColor = getColor(R.color.black)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        setContent {
            MovieTicketPurchaseAppTheme {
                ScreenNavigation()
            }
        }
    }
}
