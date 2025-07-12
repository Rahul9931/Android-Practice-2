package com.example.android_practice_2.di.dagger2.service

import com.example.android_practice_2.di.dagger2.di.qualifier.FirebaseQualifier
import com.example.android_practice_2.di.dagger2.repository.UserRepository
import javax.inject.Inject
import javax.inject.Named

class UserRegistrationService @Inject constructor (
    //@Named("firebase") private val userRepository: UserRepository,
    @FirebaseQualifier private val userRepository: UserRepository,
    private val emailService: EmailService
){

    fun registerUser(email: String, password:String){
        userRepository.sendUser(email,password)
        emailService.send(email,"example@gmail.com","User Registration")
    }
}