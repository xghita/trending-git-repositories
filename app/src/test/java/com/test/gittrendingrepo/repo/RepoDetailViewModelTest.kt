package com.test.gittrendingrepo.repo

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import com.test.gittrendingrepo.base.BaseTest
import com.test.gittrendingrepo.core.events.ErrorUI
import com.test.gittrendingrepo.core.events.IdleUI
import com.test.gittrendingrepo.data.repo.Repo
import com.test.gittrendingrepo.ui.repo.detail.RepoDetailViewModel
import com.test.gittrendingrepo.ui.repo.detail.RepoDetailViewState
import com.test.gittrendingrepo.ui.repo.event.RepoEventState
import com.test.gittrendingrepo.util.TestObserver
import io.reactivex.Single
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module

private const val REPO_ID = 5152285

class RepoDetailViewModelTest : BaseTest() {
    companion object {

        lateinit var objectUnderTest: RepoDetailViewModel

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            setupBaseClass()
        }
    }

    override fun getTestModule(): List<Module> {

        val testModule = module {
            factory {
                RepoDetailViewModel(REPO_ID, repoProviderMock)
            }
        }
        return super.getTestModule().plus(testModule)
    }

    override fun afterEachTest() {
        reset(repoProviderMock)
    }

    @Test
    fun `test view state success`() {
        val response =
            factory.mockRepoDetailResponse()

        mockRepoProvider(response)
        objectUnderTest = getKoin().get()

        val viewStateLiveData = objectUnderTest.repoDetailViewState()

        val viewStateTestObserver = TestObserver.test(viewStateLiveData)
        viewStateTestObserver.assertHistorySize(1)
            .assertValue { viewState ->
                viewState == RepoDetailViewState(
                    avatarUrl = response.avatarUrl,
                    fullName = response.fullName,
                    description = response.description,
                    language = response.language,
                    stars = response.stars,
                    openIssues = response.openIssues,
                    forks = response.forks,
                    gitUrl = response.gitUrl
                )
            }
        viewStateLiveData.removeObserver(viewStateTestObserver)

        val eventState = objectUnderTest.eventsState()
        val eventStateTestObserver = TestObserver.test(eventState)
        eventStateTestObserver.assertHistorySize(1)
            .assertValue(RepoEventState(repoIdleUI = IdleUI))
        eventState.removeObserver(eventStateTestObserver)
    }

    @Test
    fun `test view state error`() {
        mockRepoProvider()
        objectUnderTest = getKoin().get()

        val viewStateLiveData = objectUnderTest.repoDetailViewState()

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
        repoDetailResponse: Repo.Detail? = null
    ) {

        if (repoDetailResponse == null) {
            whenever(repoProviderMock.getRepoDetail(REPO_ID)).doReturn(
                Single.error(getGenericError())
            )
            return
        }

        whenever(repoProviderMock.getRepoDetail(REPO_ID)).doReturn(
            Single.just(repoDetailResponse)
        )
    }
}