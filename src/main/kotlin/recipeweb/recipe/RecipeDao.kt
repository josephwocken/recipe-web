package recipeweb.recipe

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import ratpack.func.Action
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

    fun getRecipeByName(name: String): Promise<Recipe?> {
        val selectRecipeByName = "SELECT id, name, content, created, last_modified " +
                "FROM recipe " +
                "WHERE name = '$name'"
        var recipe: Recipe? = null
        return Blocking.get {
            connection.createStatement().use { statement: Statement ->
                try {
                    val rs = statement.executeQuery(selectRecipeByName)
                    while (rs.next()) {
                        recipe = mapToRecipe(rs)
                        return@use
                    }
                } catch (exception: Exception) {
                    println("Failed to get recipe by name. ${exception.message}")
                    throw exception
                }
            }
            recipe
        }
    }

    fun deleteRecipe(recipeId: String): Operation {
        val deleteRecipe = "DELETE FROM recipe WHERE id = $recipeId"
        return Blocking.get {
                    connection.createStatement().use { statement: Statement ->
                        try {
                            statement.executeUpdate(deleteRecipe)
                        } catch (exception: Exception) {
                            println("Failed to delete recipe $recipeId. ${exception.message}")
                            throw exception
                        }
                    }
                }
                .operation()
    }

    fun updateRecipe(name: String?, content: String?, recipeId: String): Promise<Recipe> {
        val updateRecipeName = "UPDATE recipe SET name = '$name' " +
                "WHERE id = ${recipeId.toInt()}"
        val updateRecipeContent = "UPDATE recipe SET content = '$content' " +
                "WHERE id = ${recipeId.toInt()}"
        return selectRecipeById(recipeId)
                .map { recipe: Recipe? ->
                    if (null === recipe) {
                        throw RecipeUpdateException()
                    }
                    recipe!!
                }
                .nextOp { recipe: Recipe ->
                    if (name !== null && recipe.name !== name) {
                        Blocking.get {
                            connection.createStatement().use { statement: Statement ->
                                try {
                                    statement.executeUpdate(updateRecipeName)
                                } catch (exception: Exception) {
                                    println("Failed to update recipe name. recipe-id=$recipeId. ${exception.message}")
                                    throw exception
                                }
                            }
                        }
                                .operation()
                    } else {
                        Operation.noop()
                    }
                }
                .nextOp { recipe: Recipe ->
                    if (content !== null && recipe.content !== content) {
                        Blocking.get {
                            connection.createStatement().use { statement: Statement ->
                                try {
                                    statement.executeUpdate(updateRecipeContent)
                                } catch (exception: Exception) {
                                    println("Failed to update recipe content. $recipeId. ${exception.message}")
                                    throw exception
                                }
                            }
                        }
                                .operation()
                    } else {
                        Operation.noop()
                    }
                }
                .flatMap {
                    selectRecipeById(recipeId)
                }
                .map { recipe: Recipe? ->
                    if (null === recipe) {
                        throw RecipeUpdateException()
                    }
                    recipe
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