package recipeweb.recipe

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class RecipeResponse(
        @JsonProperty("recipes") val recipes: List<RecipeSummary>
)

data class RecipeSummary(
        val recipeId: Int,
        val content: String,
        val name: String
)

data class Recipe(
        val recipeId: Int,
        val content: String,
        val name: String,
        val created: LocalDateTime,
        val lastModified: LocalDateTime
)

data class CreateRecipeRequest(
        val name: String,
        val content: String,
        val password: String
)