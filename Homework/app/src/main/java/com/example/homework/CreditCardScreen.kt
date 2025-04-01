package com.example.homework

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.res.dimensionResource
import kotlin.random.Random

data class MyItem(val itemName: String, val itemPrice: Int, val itemColor: Color)

@Composable
fun CreditCardScreen() {
    val stuffList = remember { RandomPurchases() }
    var otpCode by remember { mutableStateOf("") }
    var otpReady by remember { mutableStateOf(false) }
    val totalAmount = stuffList.sumOf { it.itemPrice }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.spacing_medium))
    ) {
        Text(text = "Credit Card", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_medium))) {
                items(stuffList) { thing ->
                    PurchaseItem(thing)
                }
                item {
                    TotalDisplay(totalAmount)
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        AccountPart()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

        OtpPart(
            otpCode = otpCode,
            onGetOtp = {
                otpCode = (100000..999999).random().toString()
                otpReady = true
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        PayButton(totalAmount, otpReady)
    }
}

@Composable
fun PurchaseItem(item: MyItem) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_small))
            .clickable {
                Toast.makeText(
                    context,
                    "Item: ${item.itemName} - Price: $${item.itemPrice}",
                    Toast.LENGTH_SHORT
                ).show()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(item.itemColor),
                contentAlignment = Alignment.Center
            ) {
                val iconRes = if (item.itemName == "Buy Camera :0") R.drawable.camera else R.drawable.television
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
            Text(
                text = item.itemName,
                fontSize = dimensionResource(id = R.dimen.text_size_medium).value.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = "-$${item.itemPrice}",
            fontSize = dimensionResource(id = R.dimen.text_size_medium).value.sp,
            color = Color.Red
        )
    }
}

@Composable
fun TotalDisplay(total: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "TOTAL",
            fontSize = dimensionResource(id = R.dimen.text_size_medium).value.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "-$$total",
            fontSize = dimensionResource(id = R.dimen.text_size_medium).value.sp,
            color = Color.Red
        )
    }
}

@Composable
fun AccountPart() {
    val accountList = listOf(
        "4411 0000 1234",
        "4422 0000 5678",
        "4433 0000 9101"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedAccount by remember { mutableStateOf(accountList[0]) }

    Column {
        TextField(
            value = selectedAccount,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            accountList.forEach { account ->
                DropdownMenuItem(onClick = {
                    selectedAccount = account
                    expanded = false
                }) {
                    Text(text = account)
                }
            }
        }
    }
}

@Composable
fun OtpPart(otpCode: String, onGetOtp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Get OTP to verify information", color = Color.Gray)
            TextField(
                value = otpCode,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                label = { Text("OTP") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                )
            )

        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
        Button(
            onClick = onGetOtp,
            enabled = otpCode.isEmpty(),
            modifier = Modifier.size(160.dp, 50.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Text("Get OTP")
        }
    }
}

@Composable
fun PayButton(total: Int, otpReady: Boolean) {
    val context = LocalContext.current
    var paymentMade by remember { mutableStateOf(false) }

    Button(
        onClick = {
            Toast.makeText(context, "DAMN, you are rich. You've spent $$total!", Toast.LENGTH_SHORT).show()
            paymentMade = true
        },
        enabled = otpReady && !paymentMade,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("Pay Now")
    }
}

fun RandomPurchases(): List<MyItem> {
    val itemNames = listOf("Buy Camera :0", "Buy Television XD")
    val itemColors = listOf(
        Color(0xFF3629B7),
        Color(0xFFFF4267),
        Color(0xFFFFAF2A),
        Color(0xFF0890FE)
    )
    return List(10) {
        MyItem(
            itemName = itemNames.random(),
            itemPrice = Random.nextInt(100, 2000),
            itemColor = itemColors.random()
        )
    }
}
