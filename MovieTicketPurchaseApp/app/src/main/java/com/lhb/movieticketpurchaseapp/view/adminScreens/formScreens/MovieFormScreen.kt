package com.lhb.movieticketpurchaseapp.view.adminScreens.formScreens

import android.app.DatePickerDialog
import android.net.Uri
import android.util.Log
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.lhb.movieticketpurchaseapp.model.MovieFormData
import com.lhb.movieticketpurchaseapp.model.toFormData
import com.lhb.movieticketpurchaseapp.ui.theme.Inter
import com.lhb.movieticketpurchaseapp.utils.createFileByUri
import com.lhb.movieticketpurchaseapp.utils.createFileByUris
import com.lhb.movieticketpurchaseapp.utils.createFileVideo
import com.lhb.movieticketpurchaseapp.view.components.CustomBigButton
import com.lhb.movieticketpurchaseapp.view.components.CustomDropDown
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText2
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlineText3
import com.lhb.movieticketpurchaseapp.view.components.CustomOutlinedTextField1
import com.lhb.movieticketpurchaseapp.view.components.TopBarForm
import com.lhb.movieticketpurchaseapp.view.userScreen.HorizontalPagerIndicator
import com.lhb.movieticketpurchaseapp.viewmodel.MovieGenreViewModel
import com.lhb.movieticketpurchaseapp.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MovieFormScreens(
    idUpdate: String,
    navController: NavController,
    movieViewModel: MovieViewModel,
    movieGenreViewModel: MovieGenreViewModel
) {
    val movieState = if (idUpdate != "") movieViewModel.getMovieById(idUpdate)
        .observeAsState(initial = null).value else null
    Log.d("TAG", "movie details : " + movieState)
    var formData by remember(movieState) {
        mutableStateOf(movieState?.toFormData() ?: MovieFormData())
    }
    val movieGenres by movieGenreViewModel.listMovieCategory.observeAsState(initial = emptyList())
    var selectedMovieGenre by remember { mutableStateOf( movieGenres[0]) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isTitleError by remember { mutableStateOf(false) }
    var isDescriptionError by remember { mutableStateOf(false) }
    var isDirectorError by remember { mutableStateOf(false) }
    var isDurationError by remember { mutableStateOf(false) }
    var isReleaseDateError by remember { mutableStateOf(false) }
    var isPostersError by remember { mutableStateOf(false) }
    var isImagesError by remember { mutableStateOf(false) }
    var isTrailersError by remember { mutableStateOf(false) }

    // choose images, video
    var imagePoster by remember { mutableStateOf<String?>(null) }
    val laucher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val fileImage = createFileByUri(context, uri)
            fileImage.let {
                formData = formData.copy(poster = fileImage)
                Log.d("TAG", "poster : " + fileImage)
            }
        }
    }
    var videoFile by remember { mutableStateOf<String?>(null) }
    val videoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val fileVideo = context.createFileVideo(uri, "video")
                videoFile = fileVideo?.path
                fileVideo?.let { nonNullFileVideo ->
                    formData = formData.copy(trailer = nonNullFileVideo.path)
                }
            }
        }

    var imageFiles by remember { mutableStateOf<List<String>>(emptyList()) }
    val multipleImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            val files = uris.mapNotNull { uri ->
                context.createFileByUris(uri, "images")?.path
            }
            imageFiles = files
            files.let { nonNullFiles ->
                formData = formData.copy(images = nonNullFiles)
            }
            Log.d("TAG", "imageFiles: " + imageFiles)
        }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { if(imageFiles.isEmpty()) formData.images.size else imageFiles.size })
    // calendar
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EE, dd/MM/yyyy", Locale.getDefault())
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val selectedDate = dateFormat.format(calendar.time)
            formData = formData.copy(releaseDate = selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    // radioButton status
    var selectedStatusIndex by remember(movieState) { mutableIntStateOf(formData.status) }
    val statusOptions = listOf("Coming Soon", "New")
    Scaffold(
        containerColor = Color(0xff14111e),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopBarForm(
                title = if (idUpdate == "") "Create Movie" else "Update Movie",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Poster",
                        color = Color(0xffffffff),
                        fontSize = 14.sp, fontFamily = Inter,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .aspectRatio(9 / 13.7f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = if (isPostersError) Color(0xffb1261c) else Color(0xff6C47DB),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                laucher.launch("image/*")
                                isPostersError = false
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (formData.poster.isNotEmpty()) {
                            Image(
                                painter = rememberImagePainter(
                                    ImageRequest.Builder(context)
                                        .data(formData.poster)
                                        .build()
                                ),
                                contentDescription = null,
                                modifier = Modifier.aspectRatio(9 / 13.7f),
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
                // video
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Trailer",
                        color = Color(0xffffffff),
                        fontSize = 14.sp, fontFamily = Inter,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .aspectRatio(16 / 9f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = if (isTrailersError) Color(0xffb1261c) else Color(0xff6C47DB),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                videoLauncher.launch("video/*")
                                isTrailersError = false
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (formData.trailer != "") {
                            AndroidView(
                                factory = { context ->
                                    VideoView(context).apply {
                                        setVideoURI(Uri.parse(formData.trailer))
                                        setOnPreparedListener {
                                            it.isLooping = true
                                            start()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(16 / 9f)
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
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Movie images",
                    color = Color(0xffffffff),
                    fontSize = 14.sp, fontFamily = Inter,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 7.5f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = if (isImagesError) Color(0xffb1261c) else Color(0xff6C47DB),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            multipleImageLauncher.launch("image/*")
                            isImagesError = false
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (imageFiles.isNotEmpty() || (idUpdate != "" && formData.images.isNotEmpty())) {
                        val images = imageFiles.ifEmpty { formData.images.toList() }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            userScrollEnabled = true,
                        ) { page ->
                            Image(
                            painter = rememberImagePainter(data =images[page]),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f),
                            contentScale = ContentScale.Crop,
                            )
                        }
                        HorizontalPagerIndicator(
                            pagerState = pagerState,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(10.dp),
                            activeColor = Color.White,
                            inactiveColor = Color.Gray
                        )
                    }  else {
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
            //
            CustomOutlinedTextField1(
                value = formData.title,
                onValueChange = {
                    formData = formData.copy(title = it)
                    isTitleError = false
                },
                icon = Icons.Outlined.DriveFileRenameOutline,
                label = "Title",
                isContentError = isTitleError,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomDropDown(
                listItems = movieGenres,
                initialSelectedItem = if (idUpdate != "") formData.genre else "",
                onItemSelected = { genre ->
                    selectedMovieGenre = genre
                    formData = formData.copy(genre = genre)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField1(
                value = formData.description,
                onValueChange = {
                    formData = formData.copy(description = it)
                    isDescriptionError = false
                },
                icon = Icons.Outlined.DriveFileRenameOutline,
                label = "Description",
                isContentError = isDescriptionError,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomOutlinedTextField1(
                value = formData.director,
                onValueChange = {
                    formData = formData.copy(director = it)
                    isDirectorError = false
                },
                icon = Icons.Outlined.Person,
                label = "Director",
                isContentError = isDirectorError,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomOutlineText2(
                    value = if (formData.duration == 0) "" else formData.duration.toString(),
                    onValueChange = { newValue ->
                        formData = formData.copy(duration = newValue.toIntOrNull() ?: 0)
                        isDurationError = false
                    },
                    icon = Icons.Outlined.Timer,
                    label = "Duration ",
                    isContentError = isDurationError,
                    modifier = Modifier.weight(1f),
                    placeholder = "min",
                    onclick = {}
                )
                Spacer(modifier = Modifier.width(10.dp))
                CustomOutlineText3(
                    value = formData.releaseDate,
                    onValueChange = {
                        formData = formData.copy(releaseDate = it)
                    },
                    icon = Icons.Outlined.CalendarMonth,
                    label = "Release ",
                    isContentError = isReleaseDateError,
                    modifier = Modifier.weight(1f),
                    onclick = {
                        datePickerDialog.show()
                        isReleaseDateError = false
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            // status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                statusOptions.forEachIndexed { index, status ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            selectedStatusIndex = index
                            formData = formData.copy(status = index)
                        }
                    ) {
                        RadioButton(
                            selected = selectedStatusIndex == index,
                            onClick = {
                                selectedStatusIndex = index
                                formData = formData.copy(status = index)
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = status,
                            color = Color(0xffffffff),
                            fontFamily = Inter,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            //
            Spacer(modifier = Modifier.height(50.dp))
            CustomBigButton(
                title = "SAVE",
                onClick = {
                    if (formData.poster == "") {
                        isPostersError = true
                        return@CustomBigButton
                    }
                    if (formData.trailer == "") {
                        isTrailersError = true
                        return@CustomBigButton
                    }
                    if (formData.images.isEmpty()) {
//                        Log.d("MovieForm", "Status images :"+formData.images.isEmpty())
                        isImagesError = true
                        return@CustomBigButton
                    }
                    if (formData.title == "") {
                        isTitleError = true
                        return@CustomBigButton
                    }
                    if (formData.description == "") {
                        isDescriptionError = true
                        return@CustomBigButton
                    }
                    if (formData.director == "") {
                        isDirectorError = true
                        return@CustomBigButton
                    }
                    if (formData.duration == 0) {
                        isDurationError = true
                        return@CustomBigButton
                    }
                    if (formData.releaseDate == "") {
                        isReleaseDateError = true
                        return@CustomBigButton
                    }
                    //
                    if (idUpdate == "") {
                        Log.d("TAG", "formData : " + formData)
                        movieViewModel.addNewMovie(formData) { success ->
                            coroutineScope.launch {
                                if (success) {
                                    Toast.makeText(context, "Add movie successfully", Toast.LENGTH_SHORT).show()
                                    formData = MovieFormData()
                                    imageFiles = emptyList()
                                    selectedMovieGenre = movieGenres[0]
                                    selectedStatusIndex = 0
                                } else {
                                    Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        movieViewModel.updateMovie(idUpdate, formData) { success ->
                            coroutineScope.launch {
                                if (success) {
                                    Toast.makeText(context, "Update movie successfully", Toast.LENGTH_SHORT).show()
                                    formData = MovieFormData()
                                    imageFiles = emptyList()
                                    selectedMovieGenre = movieGenres[0]
                                    selectedStatusIndex = 0
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}