package io.github.mattpvaughn.pokeapp.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon


private const val MODEL_DATABASE_NAME = "model_db"

private lateinit var INSTANCE: ModelDatabase
fun getModelDatabase(context: Context): ModelDatabase {
    synchronized(ModelDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ModelDatabase::class.java,
                MODEL_DATABASE_NAME
            ).build()
        }
    }
    return INSTANCE
}

@Database(entities = [Pokemon::class], version = 2, exportSchema = false)
abstract class ModelDatabase : RoomDatabase() {
    abstract val modelDao: ModelDao
}

@Dao
interface ModelDao {
    @Query("SELECT * FROM Pokemon")
    fun getAllRows(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM Pokemon")
    fun getModels(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rows: List<Pokemon>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getModel(id: Int): LiveData<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE name LIKE :query")
    fun search(query: String): LiveData<List<Pokemon>>

    @Query("SELECT * FROM Pokemon WHERE name LIKE :query")
    fun searchAsync(query: String): List<Pokemon>

    @Query("UPDATE Pokemon SET name = :text WHERE id = :modelId")
    fun updateText(modelId: Int, text: String)

    @Query("DELETE FROM Pokemon")
    suspend fun clear()

    @Query("SELECT * FROM Pokemon WHERE id = :modelId LIMIT 1")
    suspend fun getModelAsync(modelId: Int): Pokemon
}


