package recipeweb.recipe

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDateTime
import java.time.ZoneId

class RecipeDao @Inject constructor(
        private val connection: Connection
) {

    fun selectRecipeById(recipeId: String): Promise<Recipe?> {
        val selectById = "SELECT id, name, content, created, last_modified " +
                "FROM recipe " +
                "WHERE id = $recipeId"
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
        val selectAll = "SELECT id, name, content, created, last_modified FROM recipe"
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
        val insertRecipe = "INSERT INTO recipe " +
                "(name, content, created, last_modified) " +
                "VALUES (?,?,?,?)"
        val now = LocalDateTime.now(ZoneId.of("UTC"))
        return Blocking.op {
            val pStatement: PreparedStatement = connection.prepareStatement(insertRecipe)
            pStatement.setString(1, recipe.name)
            pStatement.setString(2, recipe.content)
            pStatement.setObject(3, now)
            pStatement.setObject(4, now)
            pStatement.executeUpdate()
            pStatement.close()
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
        val created = resultSet.getObject("created", LocalDateTime::class.java)
        val lastModified = resultSet.getObject("last_modified", LocalDateTime::class.java)
        return Recipe(
                recipeId = recipeId,
                content = content,
                name = name,
                created = created,
                lastModified = lastModified
        )
    }


}