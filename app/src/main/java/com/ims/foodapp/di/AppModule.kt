package com.ims.foodapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ims.foodapp.api.MealApi
import com.ims.foodapp.constants.Constants.BASE_URL
import com.ims.foodapp.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(api:MealApi): MealRepository
    = MealRepository(api)

    @Singleton
    @Provides
    fun provideApi():MealApi
    = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MealApi::class.java)


    @Singleton
    @Provides
    fun provideAuth():FirebaseAuth
    = Firebase.auth

    @Singleton
    @Provides
    fun provideDb():FirebaseFirestore
    = Firebase.firestore
}