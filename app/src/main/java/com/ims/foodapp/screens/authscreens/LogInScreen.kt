package com.ims.foodapp.screens.authscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ims.foodapp.R
import com.ims.foodapp.common.InputField
import com.ims.foodapp.common.LoadingSpinner
import com.ims.foodapp.navigation.Screens
import com.ims.foodapp.screens.FireViewModel

@Composable
fun LogInScreen(navController: NavController, fireViewModel: FireViewModel){
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Box (modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.background), contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight)

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(150.dp))


            Image(painter = painterResource(id = R.drawable.food_app_logo), contentDescription = null,
                modifier = Modifier.size(200.dp))

            InputField(input = email, placeholder = "Enter your email", "Email", loading = fireViewModel.signLoading)

            InputField(input = password, placeholder = "Enter your password", "Password", loading = fireViewModel.signLoading,
                password = true
            )

            Button(onClick = {
                fireViewModel.login(email.value,password.value)
                email.value = ""
                password.value =""
            },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color.Red.copy(green = 0.3f)),
                enabled = !fireViewModel.signLoading.value
            ) {
                Text(text = "Sign In", fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp)
            }

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(text = "New here?", fontSize = 15.sp)
                Text(text = " Create new account",
                    color = Color.Blue.copy(alpha = 0.8f),
                    fontSize = 15.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.Signup.name){
                            popUpTo(0)
                        }
                    })
            }

        }

        if (fireViewModel.signLoading.value){
            LoadingSpinner(color = Color.Red.copy(green = 0.3f))
        }
    }
}
