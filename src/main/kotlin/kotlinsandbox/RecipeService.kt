package kotlinsandbox

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
                        )
                )
        )
    }

}