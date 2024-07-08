package com.lhb.movieticketpurchaseapp.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.lhb.movieticketpurchaseapp.model.MovieType
import com.lhb.movieticketpurchaseapp.ui.theme.Inter

@Composable
fun ItemMovieGenre(
    index: Int,
    movieType: MovieType,
    onEditClick: (id:String) -> Unit,
    onDeleteClick: (id: String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color("#000000".toColorInt()).copy(alpha = 0.8f),
            contentColor = Color("#ffffff".toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 4.dp,
            focusedElevation = 4.dp
        ),
        modifier = Modifier
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0xff6C47DB), // dark border
                spotColor = Color(0xff6C47DB)  // light border
            )
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = index.toString(),
                    fontSize = 12.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.55f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = movieType.name,
                    fontSize = 16.sp,
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEditClick(movieType.id) },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        Icons.Outlined.EditNote,
                        contentDescription = null,
                        tint = Color(0xffffffff).copy(alpha = 0.53f)
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                IconButton(
                    onClick = { onDeleteClick(movieType.id) },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = null,
                        tint = Color(0xff6C47DB).copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}