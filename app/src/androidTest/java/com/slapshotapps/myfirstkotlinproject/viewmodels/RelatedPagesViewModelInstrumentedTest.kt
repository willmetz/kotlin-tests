package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.WikiRestApiService
import com.slapshotapps.network.response.Page
import com.slapshotapps.network.response.RelatedPagesModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class RelatedPagesViewModelInstrumentedTest {
    lateinit var viewModel: RelatedPagesViewModel
    lateinit var mockService: WikiRestApiService

    @Before
    fun setUp() {

        mockService = mock<WikiRestApiService>()
        viewModel = RelatedPagesViewModel(mockService)


//        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
//        RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
//        RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @Test
    fun onViewAttached() {

        val countDownLatch = CountDownLatch(1)

        Mockito.`when`(mockService.relatedPages(ArgumentMatchers.anyString())).thenReturn(
            Observable.just(
                getData()
            )
        )

        viewModel.onViewAttached(object : RelatedPagesViewModel.RelatedPagesListener {
            override fun onDataLoaded(items: List<RelatedPagesItem>) {

                kotlin.test.assertEquals(2, items.size)

                kotlin.test.assertEquals("this is a test", items.get(0).getDescription())
                kotlin.test.assertEquals("still testing stuff", items.get(1).getDescription())

                countDownLatch.countDown()
            }

            override fun onError() {

            }
        }, "hockey")


        kotlin.test.assertEquals(true, countDownLatch.await(2, TimeUnit.SECONDS))
    }

    private fun getData(): RelatedPagesModel {

        val page1 = Page("test", "this is a test", null)
        val page2 = Page("test2", "still testing stuff", null)
        val pages = ArrayList<Page>()
        pages.add(page1)
        pages.add(page2)

        return RelatedPagesModel(pages)
    }


    inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
}