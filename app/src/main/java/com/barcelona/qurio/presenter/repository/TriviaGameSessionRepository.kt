package com.barcelona.qurio.presenter.repository

import com.barcelona.qurio.presentation.model.TriviaGameSession

interface TriviaGameSessionRepository {
    suspend fun insertSession(session: TriviaGameSession)
    suspend fun getAllSessions(): List<TriviaGameSession>
}