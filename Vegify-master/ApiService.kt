package com.example.vegify
import com.example.vegify.ui.homepageScreen.Meal
import com.example.vegify.ui.homepageScreen.MealResponse
import com.example.vegify.ui.homepageScreen.RecipeResponse
import com.example.vegify.ui.homepageScreen.categoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private val retrofit=Retrofit
    .Builder()
    //use this base url to get the data:
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    //convert that json code to usable kotlin objects:
    .addConverterFactory(GsonConverterFactory.create())
    //build the connection to this base url and do it so we can convert it to kotlin objects
    .build()

//create this using ApiService(we need to have a service that enables us to get the @GET wala url)
val recipeService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService{
    //for categories:
    @GET("categories.php")
    //this is a data class that will contain list of all categories in json
    suspend fun getCategory(): categoryResponse
    //for recipes within a category:
    @GET("filter.php")
    suspend fun getRecipeForCategory(@Query("c") category: String): RecipeResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: MealApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)
    }
}

class MealRepository {
    private val api = RetrofitInstance.api

    suspend fun getMealById(mealId: String): Meal? {
        val response = api.getMealById(mealId)
        return if (response.isSuccessful) response.body()?.meals?.firstOrNull() else null
    }
    suspend fun getMealByName(mealName: String): Meal?{
        val response=api.getMealByName(mealName)
        return if (response.isSuccessful) response.body()?.meals?.firstOrNull() else null
    }
}

interface MealApiService {

    @GET("search.php")
    suspend fun getMealByName(@Query("s") mealName: String): Response<MealResponse>

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") mealId: String): Response<MealResponse>
}