package recipeweb.recipe

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class RecipeDao @Inject constructor(
        private val connection: Connection
) {

    fun selectRecipeById(recipeId: String): Promise<Recipe?> {
        val selectById = "SELECT * FROM recipe WHERE id = $recipeId"
        var recipe: Recipe? = null
        return Blocking.get {
            connection.createStatement().use { statement: Statement ->
                val rs = statement.executeQuery(selectById)
                while (rs.next()) {
                    recipe = mapToRecipe(rs)
                    return@use
                }
            }
            recipe
        }
    }

    fun selectAllRecipes(): Promise<List<Recipe>> {
        val selectAll = "SELECT * FROM recipe"
        var recipes: List<Recipe> = listOf()
        return Blocking.get {
            connection.createStatement().use { statement: Statement ->
                val rs = statement.executeQuery(selectAll)
                recipes = mapToRecipes(rs)
            }
            recipes
        }
    }

    fun createRecipe(recipe: CreateRecipeRequest): Operation {
        val insertRecipe = "INSERT INTO recipe (name, content) VALUES ('${recipe.name}','${recipe.content}')"
        return Blocking.op {
            connection.createStatement().use { statement: Statement ->
                statement.executeUpdate(insertRecipe)
            }
        }
    }

    private fun mapToRecipes(resultSet: ResultSet): List<Recipe> {
        val recipes: MutableList<Recipe> = mutableListOf()
        while (resultSet.next()) {
            val recipe = mapToRecipe(resultSet)
            recipes.add(recipe)
        }
        return recipes
    }

    private fun mapToRecipe(resultSet: ResultSet): Recipe {
        val content = resultSet.getString("content")
        val recipeId = resultSet.getInt("id")
        val name = resultSet.getString("name")
        return Recipe(
                recipeId = recipeId,
                content = content,
                name = name
        )
    }


}