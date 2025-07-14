package com.example.vezbanje.quiz

import android.util.Log
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi
){

    //private val breedsApi: BreedsApi = retrofit.create(BreedsApi:: class.java)

//    suspend fun getBreedPhotos(breedId: String) : List<CatImage> {
//        return photosApi.getAllPhotos(breedId = breedId)
//    }

    suspend fun postScore(nickname: String, score: Float) {
        val request = AddToLeaderboardRequest(nickname, score,3)
        Log.d("reqLog",request.nickname)
        Log.d("reqLog",""+request.result)
        Log.d("reqLog",""+request.category)
        quizApi.postScore(request)
    }

}