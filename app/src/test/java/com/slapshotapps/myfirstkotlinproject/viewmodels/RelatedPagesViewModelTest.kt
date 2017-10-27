package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.WikiRestApiService
import com.slapshotapps.network.response.Page
import com.slapshotapps.network.response.RelatedPagesModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals


class RelatedPagesViewModelTest {

    lateinit var viewModel: RelatedPagesViewModel
    lateinit var mockService: WikiRestApiService

    @Before
    fun setUp() {

        mockService = mock()
        viewModel = RelatedPagesViewModel(mockService)


        //need to set the default handlers for RxJava unit tests as there is no android components to Android.Main is not
        //understood
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @Test
    fun onViewAttached() {

        val countDownLatch = CountDownLatch(1)

        //interesting here as "when" is a kotlin keyword so it needs to be escaped with the backticks
        Mockito.`when`(mockService.relatedPages(anyString())).thenReturn(
            Observable.just(
                getData()
            )
        )

        viewModel.onViewAttached(object : RelatedPagesViewModel.RelatedPagesListener {
            override fun onDataLoaded(items: List<RelatedPagesItem>) {

                assertEquals(2, items.size)
                assertEquals("description", items.get(0).getDescription())
                assertEquals("description2", items.get(1).getDescription())

                countDownLatch.countDown()

            }

            override fun onError() {
                assertEquals(false, true)
            }
        }, "hockey")


        assertEquals(true, countDownLatch.await(2, TimeUnit.SECONDS))
    }

    fun getData(): RelatedPagesModel {

        val page1 = Page("test", "description", null)
        val page2 = Page("test2", "description2", null)
        val pages = ArrayList<Page>()
        pages.add(page1)
        pages.add(page2)

        return RelatedPagesModel(pages)
    }


    inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
}