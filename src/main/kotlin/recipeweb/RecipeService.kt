package recipeweb

import ratpack.exec.Operation
import ratpack.exec.Promise

class RecipeService {

    fun createRecipe(recipe: Recipe): Operation {
        println("TODO implement")
        return Operation.noop()
    }

    fun getAllRecipes(): Promise<List<Recipe>> {
        return Promise.value(
                listOf(
                        Recipe(
                                "recipe-name",
                                "contents",
                                "link"
                        ),
                        Recipe(
                                "sophie's recipe",
                                "cook for 5 hours",
                                "https://www.bbcgoodfood.com/recipes/egg-cress-club-sandwich"
                        )
                )
        )
    }

}