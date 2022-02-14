package com.test.gittrendingrepo.repo

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.test.gittrendingrepo.base.BaseTest
import com.test.gittrendingrepo.core.events.ErrorUI
import com.test.gittrendingrepo.core.events.SuccessUI
import com.test.gittrendingrepo.data.network.model.TrendingRepoResponse
import com.test.gittrendingrepo.data.repo.Repo
import com.test.gittrendingrepo.ui.repo.event.RepoEventState
import com.test.gittrendingrepo.ui.repo.list.RepoListViewModel
import com.test.gittrendingrepo.ui.repo.list.RepoListViewState
import com.test.gittrendingrepo.util.TestObserver
import io.reactivex.Single
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito.reset

class RepoListViewModelTest : BaseTest() {

    companion object {

        lateinit var objectUnderTest: RepoListViewModel

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            setupBaseClass()
        }
    }

    override fun getTestModule(): List<Module> {

        val testModule = module {
            factory {
                RepoListViewModel(repoProviderMock)
            }
        }
        return super.getTestModule().plus(testModule)
    }

    override fun afterEachTest() {
        reset(repoProviderMock)
    }

    @Test
    fun `test view state empty list`() {
        mockRepoProvider(RepoProviderData.EMPTY)
        objectUnderTest = getKoin().get()

        val viewStateLiveData = objectUnderTest.repoListViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertHistorySize(1)
            .assertValue { viewState ->
                viewState == RepoListViewState(
                    listOf(),
                    false
                )
            }
        viewStateLiveData.removeObserver(viewStateTestObserver)
    }

    @Test
    fun `test view state success`() {
        val response =
            factory.mockTrendingRepoResponse(RepoListViewModelTest::class.java.classLoader!!)

        mockRepoProvider(RepoProviderData.LIST, response)
        objectUnderTest = getKoin().get()

        val viewStateLiveData = objectUnderTest.repoListViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertHistorySize(1)
            .assertValue { viewState ->
                viewState == RepoListViewState(
                    response.items?.map {
                        Repo.ListItem(
                            id = it.id,
                            name = it.name,
                            avatarUrl = it.owner.avatarUrl,
                            language = it.language,
                            watchers = it.watchers,
                            isFavorite = false
                        )
                    },
                    false
                )
            }
        viewStateLiveData.removeObserver(viewStateTestObserver)

        val eventState = objectUnderTest.eventsState()
        val eventStateTestObserver = TestObserver.test(eventState)
        eventStateTestObserver.assertHistorySize(1)
            .assertValue(RepoEventState(repoSuccessUI = SuccessUI()))
        eventState.removeObserver(eventStateTestObserver)
    }

    @Test
    fun `test view state error`() {
        mockRepoProvider(RepoProviderData.ERROR)
        objectUnderTest = getKoin().get()

        val viewStateLiveData = objectUnderTest.repoListViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertNoValue()
        viewStateLiveData.removeObserver(viewStateTestObserver)

        val eventState = objectUnderTest.eventsState()
        val eventStateTestObserver = TestObserver.test(eventState)
        eventStateTestObserver.assertHistorySize(1)
            .assertValue(RepoEventState(repoErrorUI = ErrorUI(description = getGenericError().localizedMessage)))
        eventState.removeObserver(eventStateTestObserver)
    }

    private fun mockRepoProvider(
        providerData: RepoProviderData,
        trendingRepoResponse: TrendingRepoResponse = TrendingRepoResponse(
            0, false,
            listOf()
        )
    ) {
        when (providerData) {
            RepoProviderData.EMPTY, RepoProviderData.LIST -> {
                whenever(repoProviderMock.getRepoList()).doReturn(
                    Single.just(
                        trendingRepoResponse
                    )
                )
            }
            RepoProviderData.ERROR -> {
                whenever(repoProviderMock.getRepoList()).doReturn(
                    Single.error(getGenericError())
                )
            }
        }
    }

    enum class RepoProviderData {
        EMPTY,
        LIST,
        ERROR
    }
}
