package com.barcelona.qurio.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.barcelona.qurio.model.local.entity.TriviaGameSession

@Dao
interface GameSessionDao {
    @Insert
    suspend fun insertSession(session: TriviaGameSession)

    @Query("SELECT * FROM game_session ORDER BY date DESC")
    suspend fun getAllSessions(): List<TriviaGameSession>
}