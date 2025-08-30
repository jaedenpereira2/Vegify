package com.example.vegify.ui.searchsection

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

suspend fun fetchRecipe(
    ingredients: String,
    servings: String,
    timeOnHand: String): String
{
    val ingredientsString = ingredients.trim()
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // Load API key from environment variable or local.properties (for development only)
    val apiKey = System.getenv("VEGIFY_API_KEY") ?: ""
    if (apiKey.isEmpty()) throw IllegalStateException("API key not set. Please set VEGIFY_API_KEY as an environment variable.")

    val systemPrompt = """
        You are an assistant that receives a list of ingredients, number of servings, and time on hand. Suggest a recipe with proper sections: first, the name of the dish, then ingredients, and then instructions.
    """.trimIndent()

    val request = ChatRequest(
        model = "mistralai/Mixtral-8x7B-Instruct-v0.1",
        messages = listOf(
            ChatMessage("system", systemPrompt),
            ChatMessage("user", "I have $ingredientsString. I want to make $servings servings in $timeOnHand minutes.")
        ),
        maxTokens = 1024
    )

    return try {
        val response: ChatResponse = client.post("https://api-inference.huggingface.co/v1/chat/completions") {
            header("Authorization", "Bearer $apiKey")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()


        response.choices.first().message.content
    } finally {
        client.close()
    }
}