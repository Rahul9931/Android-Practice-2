package com.example.android_practice_2.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import okhttp3.OkHttpClient

object ApiClient {
    fun  ApolloClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://demomobikul.webkul.in/mobikul1/pub/graphql")
            .addHttpHeader("fcmToken", "dyNpQsSDSzGEiXvyH0iraj:APA91bFFSnnrrG0aJtAdKTBeOAjhc6FGLcCktR2tH4R4p9RpcoHtDlGkdJyihSO5Wb8D5z5MBWMRLXbxpvGDoyHccOyK3XRpPKzJLYHM7zApK_IISrAGIxN8vlNY23X5P23V91V9INOh")
            .addHttpHeader("fcmDeviceId", "AP31.240617.003")
            .addHttpHeader("IsGuest", "1")  // Ensure it's passed as a String
            .okHttpClient(okHttpClient)
            .build()
        return apolloClient
    }
}