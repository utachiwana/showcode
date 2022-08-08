package com.utachiwana.messenger.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import com.utachiwana.messenger.R
import com.utachiwana.messenger.appComponent
import com.utachiwana.messenger.data.local.AppSharedPreferences
import com.utachiwana.messenger.data.pojo.CurrentWeather
import com.utachiwana.messenger.ui.MainViewModel
import javax.inject.Inject

class ComposeMainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var authPref: AppSharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
        viewModel.loadHistoryWeather()

    }


    @Preview
    @Composable
    fun MainScreen() {
        val focus = LocalFocusManager.current
        val weathers = viewModel.historyWeather.observeAsState()
        val cities = viewModel.cities.observeAsState()
        val textInput = remember { mutableStateOf("") }
        val showDropDown = remember { mutableStateOf(false) }
        val showDialog = remember { mutableStateOf(false) }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog.value = true },
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.logout),
                        modifier = Modifier.padding(20.dp, 5.dp),
                        fontSize = 18.sp
                    )
                }
            }
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Box {
                        OutlinedTextField(
                            value = textInput.value,
                            onValueChange = { newText ->
                                textInput.value = newText
                                viewModel.searchCity(newText)
                                showDropDown.value = newText.isNotEmpty()
                            },
                            textStyle = TextStyle(fontSize = 18.sp),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.input_city),
                                    fontSize = 18.sp
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardActions = KeyboardActions(onDone = {
                                focus.clearFocus(true)
                            })
                        )
                        DropdownMenu(
                            expanded = showDropDown.value,
                            onDismissRequest = { },
                            properties = PopupProperties(focusable = false),
                            offset = DpOffset(5.dp, 0.dp),
                        ) {
                            cities.value?.forEach { city ->
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        textInput.value = city.name
                                        showDropDown.value = false
                                        viewModel.getCityWeather(city)
                                    }
                                ) {
                                    Text(text = city.name)
                                }
                            }
                        }
                    }
                    LazyColumn {
                        weathers.value?.let {
                            items(it) { itemWeather ->
                                WeatherItem(item = itemWeather)
                            }
                        }
                    }
                }
                if (viewModel.loading.observeAsState().value == true) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                if (showDialog.value) {
                  LogoutDialog(showDialog)
                }
            }
        }
    }

    @Composable
    fun WeatherItem(item: CurrentWeather?) {
        item ?: return
        Surface(
            shape = RoundedCornerShape(3.dp),
            elevation = 3.dp,
            modifier = Modifier.padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = item.getPictureLink(),
                            contentDescription = null,
                            placeholder = painterResource(id = R.drawable.ic_loading),
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Fit
                        )
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = item.name,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.align(Alignment.End)
                            )
                            item.getInlineTemperature(
                                size = 26.sp,
                                mod = Modifier.align(Alignment.Start)
                            )
                            item.getInlineWindSpeed(
                                size = 26.sp,
                                mod = Modifier.align(Alignment.Start)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        text = item.getWeatherDesc(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 16.sp,
                    )
                }
                Text(
                    text = item.getDate(),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth(0.3f),
                    color = colorResource(id = android.R.color.darker_gray)
                )
            }
        }
    }

    @Composable
    fun LogoutDialog(showDialog: MutableState<Boolean>) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            confirmButton = {
                Button(
                    onClick = { logout() },
                    content = { Text(text = stringResource(id = R.string.yes)) }
                )
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                    content = { Text(text = stringResource(id = R.string.cancel)) }
                )
            },
            title = { Text(text = stringResource(id = R.string.logout), fontSize = 20.sp) },
            text = { Text(text = stringResource(id = R.string.logout_accept), fontSize = 18.sp) },
        )
    }

    private fun logout() {
        authPref.logout()
        startActivity(Intent(this, StartActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}