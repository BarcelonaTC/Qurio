package com.barcelona.qurio.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BasePresenter<V : BaseView> {
    protected var view: V? = null
    private val presenterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun destroyPresenter() {
        presenterScope.cancel()
    }

    protected fun <T> tryToCall(
        block: suspend () -> T,
        onStart: suspend () -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onEnd: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) = presenterScope.launch(dispatcher) {
        withContext(Dispatchers.Main) { onStart() }
        runCatching { block() }
            .onSuccess { result ->
                withContext(Dispatchers.Main) { onSuccess(result) }
            }
            .onFailure { error ->
                withContext(Dispatchers.Main) { onError(error) }
            }
        withContext(Dispatchers.Main) { onEnd() }
    }

}