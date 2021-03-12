package recipeweb.recipe

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.exec.Operation
import ratpack.exec.Promise
import recipeweb.recipe.Recipe
import recipeweb.user.UserService

class RecipeService @Inject constructor(
        private val userService: UserService,
        private val recipeDao: RecipeDao
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(RecipeService::class.java)
    }

    fun getRecipe(recipeId: String): Promise<Recipe?> {
        log.info("getting recipe $recipeId")
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

    fun deleteRecipe(recipeId: String, password: String): Operation {
        return userService.validatePassword(password)
                .flatMap {
                    recipeDao.deleteRecipe(recipeId)
                            .promise()
                }
                .operation()
    }

    fun updateRecipe(updateRequest: UpdateRecipeRequest): Promise<Recipe> {
        return userService.validatePassword(updateRequest.password)
                .flatMap(
                        recipeDao.updateRecipe(
                                recipeId = updateRequest.recipeId,
                                name = updateRequest.name,
                                content = updateRequest.content
                        )
                )
    }

}