package recipeweb.recipe

import com.google.inject.Inject
import ratpack.exec.Operation
import ratpack.exec.Promise
import recipeweb.recipe.Recipe
import recipeweb.user.UserService

class RecipeService @Inject constructor(
        private val userService: UserService,
        private val recipeDao: RecipeDao
) {

    fun getRecipe(recipeId: String): Promise<Recipe?> {
        println("getting recipe $recipeId")
        return recipeDao.selectRecipeById(recipeId)
    }

    fun createRecipe(recipe: CreateRecipeRequest): Promise<Recipe?> {
        return userService.validatePassword(recipe.password)
                .next(recipeDao.createRecipe(recipe))
                .flatMap(recipeDao.getRecipeByName(recipe.name))
    }

    fun getAllRecipes(): Promise<List<Recipe>> {
        return recipeDao.selectAllRecipes()
    }

    fun deleteRecipe(recipeId: String): Operation {
        return recipeDao.deleteRecipe(recipeId)
    }

}