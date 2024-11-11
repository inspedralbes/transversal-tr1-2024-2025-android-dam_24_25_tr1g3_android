package com.mbad.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mbad.ui.theme.MBADTheme
import com.mbad.R



@Composable
fun StartScreen(
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier){
        Image(
            painter = painterResource(id = R.drawable.start_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F,
            modifier = Modifier.fillMaxSize()
        )
        Button(
            onClick = onNextButtonClicked,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Entrar")
        }
    }
}

@Preview
@Composable
fun StartShopPreview(){
    MBADTheme {
        StartScreen(
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}