package com.example.vezbanje.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vezbanje.breeds.repository.BreedsDetailsRepository
import com.example.vezbanje.gallery.api.model.CatImage
import com.example.vezbanje.gallery.repository.GalleryRepository
import com.example.vezbanje.quiz.model.QuizQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val breedsDetailsRepository: BreedsDetailsRepository,
    private val quizRepository: QuizRepository
): ViewModel() {

    private val _state = MutableStateFlow(QuizQuestionState())
    val state = _state.asStateFlow()
    private fun setState(reducer: QuizQuestionState.() -> QuizQuestionState) = _state.getAndUpdate(reducer)

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val events = MutableSharedFlow<QuizEvent>()
    fun setEvent(event: QuizEvent) = viewModelScope.launch { events.emit(event) }

    init {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try{
                val quizQuestions = getQuizQuestions()
                setState { copy(items = quizQuestions) }
            }catch (error: IOException){
                setState { copy(error = error) }
            } finally {
                setState { copy(loading = false) }
            }
        }
        observeEvents()
    }

    suspend private fun getTwoRandomImages(): List<CatImage>{
        val image1 = withContext(Dispatchers.IO){
            galleryRepository.getRandomPhoto()
        }
        val image2 = withContext(Dispatchers.IO){
            galleryRepository.getRandomPhoto()
        }
        return listOf(image1,image2)
    }

    private fun getRandomQuestion(): String{
        val rand = Random.nextInt(2)
        if(rand==0){
            return "Koja macka je u proseku teza?"
        }else{
            return "Koja macka u proseku zivi duze?"
        }
    }

    private suspend fun getQuizQuestions(): List<QuizQuestion>{
        var list = mutableListOf<QuizQuestion>()
        for(i in 1..20){
            var question = getRandomQuestion()
            var catImages = getTwoRandomImages()
            var details1 = withContext(Dispatchers.IO){
                breedsDetailsRepository.getBreedDetailsById(catImages[0].breedId)
            }
            var details2 = withContext(Dispatchers.IO){
                breedsDetailsRepository.getBreedDetailsById(catImages[1].breedId)
            }
            Log.d("glavno","Id i breed id od prve ${catImages[0].id} ${catImages[0].breedId}")
            Log.d("glavno","Id i breed id od druge ${catImages[1].id} ${catImages[1].breedId}")
            if(question.equals("Koja macka je u proseku teza?")){
                while(getAverage(details1.weight_imperial)==getAverage(details2.weight_imperial)){
                    catImages = getTwoRandomImages()
                    details1 = withContext(Dispatchers.IO){
                        breedsDetailsRepository.getBreedDetailsById(catImages[0].breedId)
                    }
                    details2 = withContext(Dispatchers.IO){
                        breedsDetailsRepository.getBreedDetailsById(catImages[1].breedId)
                    }
                }
                var answer=-1
                if(getAverage(details1.weight_imperial)>getAverage(details2.weight_imperial)){
                    answer = 1
                }else{
                    answer = 2
                }
                Log.d("glavno","ID lifespan weight prve ${details1.id} ${details1.life_span} ${details1.weight_imperial}")
                Log.d("glavno","ID lifespan weight druge ${details2.id} ${details2.life_span} ${details2.weight_imperial}")
                Log.d("glavno","resenje: ${answer}")
                list.add(QuizQuestion(question,catImages[0].url,catImages[1].url,answer))
            }else{
                while(getAverage(details1.life_span)==getAverage(details2.life_span)){
                    catImages = getTwoRandomImages()
                    details1 = withContext(Dispatchers.IO){
                        breedsDetailsRepository.getBreedDetailsById(catImages[0].breedId)
                    }
                    details2 = withContext(Dispatchers.IO){
                        breedsDetailsRepository.getBreedDetailsById(catImages[1].breedId)
                    }
                }
                var answer=-1
                if(getAverage(details1.life_span)>getAverage(details2.life_span)){
                    answer = 1
                }else{
                    answer = 2
                }
                Log.d("glavno","ID lifespan weight prve ${details1.id} ${details1.life_span} ${details1.weight_imperial}")
                Log.d("glavno","ID lifespan weight druge ${details2.id} ${details2.life_span} ${details2.weight_imperial}")
                Log.d("glavno","resenje: ${answer}")
                list.add(QuizQuestion(question,catImages[0].url,catImages[1].url,answer))
            }
        }
        return list
    }

    private fun getAverage(weight: String): Float{
        val parts = weight.trim().split("-")
        val num1 = parts[0].trim().toFloat()
        val num2 = parts[1].trim().toFloat()
        return (num1 + num2) / 2
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect{
                when(it){
                    is QuizEvent.ShareScore -> {
                        share(it.nickname, it.score)
                    }
                }
            }
        }
    }

    private fun share(nickname: String, score: Float) {
        viewModelScope.launch {
            val response = quizRepository.postScore(nickname,score)
        }
    }

}

