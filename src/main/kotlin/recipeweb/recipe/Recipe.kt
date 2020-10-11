package recipeweb.recipe

import com.fasterxml.jackson.annotation.JsonProperty

data class RecipeResponse(
        @JsonProperty("recipes") val recipes: List<Recipe>
)

data class Recipe(val recipeId: Int, val content: String, val name: String)

data class CreateRecipeRequest(val name: String, val content: String)