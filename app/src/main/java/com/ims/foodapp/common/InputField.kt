package com.ims.foodapp.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ims.foodapp.R

@Composable
fun InputField(input: MutableState<String>, placeholder:String, label:String, loading: MutableState<Boolean>,
               password:Boolean = false){
    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(value = input.value, onValueChange = {input.value = it},
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.8f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
            focusedBorderColor = Color.Red.copy(0.5f),
            unfocusedBorderColor = Color.Red.copy(0.5f)),
        shape = RoundedCornerShape(corner = CornerSize(15)),
        maxLines = 1,
        singleLine = true,
        enabled = !loading.value,
        visualTransformation = if (password) {
            if (showPassword.value){
                VisualTransformation.None
        }else{
            PasswordVisualTransformation()
            }
        }else{
             VisualTransformation.None
             },
        trailingIcon = { if (password) {
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                if (showPassword.value) {
                    Icon(
                        painter = painterResource(id = R.drawable.invisible),
                        contentDescription = null, modifier = Modifier.size(23.dp)
                    )
                } else {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = null ,
                        modifier = Modifier.size(25.dp))
                }
            }
        }
        }
    )
}