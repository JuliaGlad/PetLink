package petlink.android.petlink.ui.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.cancellation.CancellationException


inline fun <T> runCatchingNonCancellation(block: () -> T): Result<T>{
    return try {
        Result.success(block())
    } catch (e: CancellationException){
        throw e
    } catch (e: Exception){
        Result.failure(e)
    }
}

suspend fun <T, R> asyncAwait(
    s1: suspend CoroutineScope.() -> T,
    transform: suspend (T) -> R
): R {
    return coroutineScope{
        val result = async(block = s1)
        transform(result.await())
    }
}

suspend fun <T1, T2, T3, R> asyncAwait(
    s1: suspend CoroutineScope.() -> T1,
    s2: suspend CoroutineScope.() -> T2,
    s3: suspend CoroutineScope.() -> T3,
    transform: suspend (T1, T2, T3) -> R
): R {
    return coroutineScope{
        val result1 = async(block = s1)
        val result2 = async(block = s2)
        val result3 = async(block = s3)
        transform(result1.await(), result2.await(), result3.await())
    }
}

suspend fun <T1, T2, R> runSequentially(
    s1: suspend () -> T1,
    s2: suspend () -> T2,
    transform: suspend (T1, T2) -> R
): R = coroutineScope {
    val r1 = s1()
    val r2 = s2()
    transform(r1, r2)
}

suspend fun <T1, T2, T3, R> runSequentially(
    s1: suspend () -> T1,
    s2: suspend () -> T2,
    s3: suspend () -> T3,
    transform: suspend (T1, T2, T3) -> R
): R = coroutineScope {
    val r1 = s1()
    val r2 = s2()
    val r3 = s3()
    transform(r1, r2, r3)
}