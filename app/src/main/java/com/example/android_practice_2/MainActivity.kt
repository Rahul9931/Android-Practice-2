package com.example.android_practice_2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.exception.ApolloException
import com.example.android_practice_2.databinding.ActivityMainBinding
import com.example.android_practice_2.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import src.main.graphql.GetHomePageDataQuery
import src.main.graphql.MutantLoginMutation
import src.main.graphql.SaveReviewMutation
import src.main.graphql.type.ReviewRatings

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var apiRes: ApolloCall<GetHomePageDataQuery.Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCallApi.setOnClickListener {

                //callGraphQLAPI()
            //callLoginMutation()
            callSaveReviewMutation()

        }
    }

    private fun callGraphQLAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.ApolloClient().query(
                    GetHomePageDataQuery(
                        Optional.presentIfNotNull(""), // customerToken
                        Optional.presentIfNotNull(1),  // storeId
                        Optional.presentIfNotNull(1),  // websiteId
                        Optional.presentIfNotNull("USD"), // currency
                        Optional.presentIfNotNull(1)  // quoteId
                    )
                ).execute()

                if (response.hasErrors()) {
                    Log.e("GraphQL_Error", "Errors: ${response.errors}")
                } else {
                    val homePageData = response.data?.homePageData
                    Log.d("GraphQL_Response", "Success: ${homePageData?.success}")
                    Log.d("GraphQL_Response", "Response: ${homePageData}")
                    Log.d("GraphQL_Response", "Store ID: ${homePageData?.storeId}")
                }
            } catch (e: ApolloException) {
                Log.e("Apollo_Exception", "GraphQL API call failed: ${e.message}")
            } catch (e: Exception) {
                Log.e("General_Exception", "Unexpected error: ${e.message}")
            }
        }
    }

    private fun callLoginMutation() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the mutation
                val response = ApiClient.ApolloClient().mutation(
                    MutantLoginMutation(
                       "test@webkul.in",  // username
                        "demo123",        // password
                        1,                // storeId
                        1,                // websiteId
                        "dttibLOTRsa_Yr3jISQRBL:APA91bHvllRsb9srFWSoT4j5kVKjgj8OZoIu0HJjCzktqr7CKIAqOQXA-4ZDxkL-IJ3KqqHg-K8kaLf3sRgb-CvWxTB-38ULaKVKdaOj6-w6gK5gM-aZXcc" // token
                    )
                ).execute()

                // Handle the response
                if (response.hasErrors()) {
                    Log.e("GraphQL_Error", "Errors: ${response.errors}")
                } else {
                    val loginData = response.data?.login
                    Log.d("GraphQL_Response", "Response${loginData}")
                    Log.d("GraphQL_Response", "Token: ${loginData?.customerToken}")
                }
            } catch (e: ApolloException) {
                Log.e("Apollo_Exception", "GraphQL API call failed: ${e.message}")
            } catch (e: Exception) {
                Log.e("General_Exception", "Unexpected error: ${e.message}")
            }
        }
    }
// https://ccce-2409-4050-2d1a-c700-b511-dc86-e9ed-1ec0.ngrok-free.app
    private fun callSaveReviewMutation() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.ApolloClient().mutation(
                    SaveReviewMutation(
                        "vlbxdn5ob6i2j1rvoh3xefoleaxeh9fw", // customerToken
                        1,  // storeId
                        4,  // productId
                        "Test",  // nickname
                        "Test details",  // detail
                        "Test title",  // title
                        listOf( // Pass ratings as a list
                            ReviewRatings(
                                Optional.present(4),  // ratingId
                                Optional.present(16)  // optionId
                            )
                        )
                    )
                ).execute()

                if (response.hasErrors()) {
                    Log.e("GraphQL_Error", "Errors: ${response.errors}")
                } else {
                    val saveReviewData = response.data?.saveReview
                    Log.d("GraphQL_Response", "Response: ${saveReviewData}")
                    Log.d("GraphQL_Response", "Message: ${saveReviewData?.message}")
                }
            } catch (e: ApolloException) {
                Log.e("Apollo_Exception", "GraphQL API call failed: ${e.message}")
            } catch (e: Exception) {
                Log.e("General_Exception", "Unexpected error: ${e.message}")
            }
        }
    }

}