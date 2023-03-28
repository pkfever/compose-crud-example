package com.test.myapplication.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.myapplication.data.entities.User
import com.test.myapplication.ui.edit.ProfileViewModel
import com.test.myapplication.ui.edit.UserEditDialog

@Composable
fun HomeScreen(homeViewModel: HomeViewModel, profileViewModel: ProfileViewModel) {

    val showDialog = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if (showDialog.value) {
        UserEditDialog(profileViewModel, dismissListener = {
            showDialog.value = !showDialog.value
            homeViewModel.getUser()
        })
    }

    BodyContent(homeViewModel, context) {
        profileViewModel.setUserId(it.id)
        showDialog.value = true
    }

}


@Composable
private fun BodyContent(
    homeViewModel: HomeViewModel,
    context: Context,
    onEditClick: (User) -> Unit
) {

    val uiState: HomeUiState by homeViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        when (uiState) {
            is HomeUiState.UserList -> {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(
                        top = 10.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    items((uiState as HomeUiState.UserList).items) {
                        UserListItem(
                            user = it,
                            context = context,
                            { onEditClick(it) }
                        ) { user -> homeViewModel.deleteUser(user) }
                        Divider(
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }

            HomeUiState.loading -> {

            }
            is HomeUiState.Error -> Text(text = (uiState as HomeUiState.Error).msg)
        }
    }
}

@Composable
private fun UserListItem(
    user: User,
    context: Context,
    onEditClick: () -> Unit,
    onDeleteBtnClick: (User) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(0.7.toFloat())) {
                Text(text = user.firstName)
                Text(text = user.email)
            }

            Row(
                modifier = Modifier.weight(0.3.toFloat()),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(com.test.myapplication.R.drawable.ic_edit),
                    contentDescription = null, // decorative
                    modifier = Modifier
                        .padding(8.dp)
//                        .size(40.dp, 40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxHeight()
                        .clickable {
                            onEditClick()
                        }
                )

                Image(
                    painter = painterResource(com.test.myapplication.R.drawable.ic_delete),
                    contentDescription = null, // decorative
                    modifier = Modifier
                        .padding(8.dp)
//                        .size(40.dp, 40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxHeight()
                        .clickable {
                            onDeleteBtnClick(user)
                        }
                )
            }
        }
    }
}