package com.ims.foodapp.screens

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.ims.foodapp.common.OrderState
import com.ims.foodapp.constants.Constants.CART
import com.ims.foodapp.constants.Constants.ORDERS
import com.ims.foodapp.constants.Constants.USERS
import com.ims.foodapp.model.Order
import com.ims.foodapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class FireViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
):ViewModel(){
    var myCart = mutableStateOf<List<Order>?>(null)
    val myOrders = mutableStateOf<List<Order>?>(null)

    val orderLoading = mutableStateOf(false)

    val signLoading = mutableStateOf(false)
    val signedIn = mutableStateOf(false)

    val userData = mutableStateOf<User?>(null)

    init {
//        signout()
        val userId = auth.currentUser?.uid
        getMyCart()
        getMyOrders()
        getUserData()

        userId?.let {
            signedIn.value = true
        }
    }

    fun login(email:String, password:String){

        signLoading.value = true

        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                getUserData()
                signLoading.value = false
                signedIn.value = true
            }
            .addOnFailureListener {

                signLoading.value = false
            }
    }

    fun signup(username:String, email: String, password: String){
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
            return
        }

        signLoading.value = true

        db.collection(USERS).whereEqualTo("Username", username).get()
            .addOnSuccessListener {doc->
                if (doc.size()>0){
                    signLoading.value = false
                }else{
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener {
                            createOrUpdateUser(username, email)
                            getUserData()
                            signLoading.value = false
                            signedIn.value = true
                        }
                        .addOnFailureListener {
                            signLoading.value = false
                        }
                }

            }

    }

    fun signout(){
        auth.signOut()
        signedIn.value = false
        myCart.value = null
        myOrders.value = null
    }

    fun getUserData(){
        val userId = auth.currentUser?.uid

        userId?.let {
        db.collection(USERS).document(userId.toString()).get()
            .addOnSuccessListener {
                val userData = it.toObject<User>()

                userData?.let {
                    this.userData.value = userData
                }
            }
        }
    }

    private fun createOrUpdateUser(username: String? = null, email: String? = null,
                                   location:String? = null, phoneNumber:String? = null){
        signLoading.value = true

        val userId = auth.currentUser?.uid

        val user = User(
            userId.toString(),
            username?:userData.value?.username,
            email?:userData.value?.email,
            location?:userData.value?.location,
            phoneNumber?:userData.value?.phoneNumber
        )

        db.collection(USERS).document(userId.toString()).get()
            .addOnSuccessListener {
                if (it.exists()){
                    it.reference.update(user.toMap())
                    signLoading.value = false
                }else{
                    db.collection(USERS).document(userId.toString()).set(user)
                    signLoading.value = false
                }
            }
    }

    fun addToCart(mealName:String, numOfMeals:String, price:String, mealImg:String, mealSize:String){
        val orderId = UUID.randomUUID().toString()
        val time = System.currentTimeMillis()

        val userId = auth.currentUser?.uid

        orderLoading.value = true

        userId?.let {uid->

            val order = Order(
                userId = uid,
                orderId = orderId,
                mealName = mealName,
                numOfMeals = numOfMeals,
                price = price,
                mealImg = mealImg,
                mealSize = mealSize,
                time = time,
                location = userData.value?.location?:"",
                phoneNumber = userData.value?.phoneNumber?:"",
                username = userData.value?.username?:""
            )

        db.collection(CART).document(orderId).set(order)
            .addOnSuccessListener {
                orderLoading.value = false
            }
            .addOnFailureListener {
                Log.d("Failed", "addToCart: $it")
                orderLoading.value = false
            }
        }
    }

    fun getMyCart(){
        val userId = auth.currentUser?.uid
        orderLoading.value = true

        db.collection(CART).whereEqualTo("userId", userId).get()
            .addOnSuccessListener {
                val cartList = it.toObjects<Order>()
                myCart.value = cartList
                orderLoading.value = false
            }
            .addOnFailureListener {
                Log.d("CartFailed", "addOrder: $it")
                orderLoading.value = false
            }
    }

    fun removeFromCart(orderId: String){
        db.collection(CART).document(orderId).delete()
            .addOnSuccessListener {
                getMyCart()
            }
    }

    fun order(orderId:String){
        orderLoading.value = true

        val finishTime = System.currentTimeMillis()

        db.collection(CART).document(orderId).get()
            .addOnSuccessListener { order->
                val finishedOrder = order.toObject<Order>()?.copy(time = finishTime, orderState = OrderState.Confirmed.text)
                db.collection(CART).document(orderId).delete()
                db.collection(ORDERS).document(orderId).set(finishedOrder?:"")

                getMyCart()
                getMyOrders()
                orderLoading.value = false
            }
            .addOnFailureListener {
                Log.d("MyOrdersFailed", "addOrder: $it")
                orderLoading.value = false
            }
    }

    fun getMyOrders(){
        orderLoading.value = true
        val userId = auth.currentUser?.uid


        db.collection(ORDERS).whereEqualTo("userId", userId).get()
            .addOnSuccessListener {
                Log.d("OrdersSucceeded", "addOrder: ")
                val ordersList = it.toObjects<Order>()
                myOrders.value = ordersList

                orderLoading.value = false
            }
            .addOnFailureListener {
                Log.d("OrdersFailed", "addOrder: $it")
                orderLoading.value = false
            }
    }

}