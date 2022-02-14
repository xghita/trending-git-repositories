package com.test.gittrendingrepo.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.test.gittrendingrepo.provider.RepoProvider
import com.test.gittrendingrepo.repo.RepoFactory
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.mockito.junit.MockitoJUnitRunner
import org.koin.test.KoinTest

@RunWith(MockitoJUnitRunner.Silent::class)
abstract class BaseTest : KoinTest {

    @get:Rule // -> allows liveData to work on different thread besides main
    var executorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    protected val repoProviderMock: RepoProvider = mock()
    protected val factory = RepoFactory()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setupBaseClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        }
    }

    open fun getTestModule(): List<Module> {
        return emptyList()
    }

    abstract fun afterEachTest()

    @Before
    fun setup() {
        startKoin {
            // declare used modules
            modules(
                getTestModule()
            )
        }
        RxJavaPlugins.setErrorHandler { e ->
            //do nothing
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        afterEachTest()
    }

    protected fun getGenericError() = Throwable("Error")
}