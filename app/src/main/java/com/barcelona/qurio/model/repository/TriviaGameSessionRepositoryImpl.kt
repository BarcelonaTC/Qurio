package com.barcelona.qurio.model.repository

import com.barcelona.qurio.model.local.dao.GameSessionDao
import com.barcelona.qurio.model.local.toEntity
import com.barcelona.qurio.model.local.toModel
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository

class TriviaGameSessionRepositoryImpl (private val gameSessionDao: GameSessionDao): TriviaGameSessionRepository {
    override suspend fun insertSession(session: TriviaGameSession) {
        gameSessionDao.insertSession(session.toEntity())
    }

    override suspend fun getAllSessions(): List<TriviaGameSession> {
        return gameSessionDao.getAllSessions().map { it.toModel() }
    }
}