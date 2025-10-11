package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.barcelona.qurio.model.local.entity.TriviaGameSessionEntity

@Dao
interface GameSessionDao {
    @Insert
    suspend fun insertSession(session: TriviaGameSessionEntity)

    @Query("SELECT * FROM game_session ORDER BY date ASC")
    suspend fun getAllSessions(): List<TriviaGameSessionEntity>
}