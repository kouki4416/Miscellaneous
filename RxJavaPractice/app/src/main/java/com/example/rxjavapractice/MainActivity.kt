package com.example.rxjavapractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.computation
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class MainActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // var name : type = create(anonymous class, arg)
        var flowable: Flowable<String> = Flowable.create(object : FlowableOnSubscribe<String> {
            override fun subscribe(emitter: FlowableEmitter<String>) {
                var myList = listOf<String>("Hello", "こんにちは")
                for (data in myList) {
                    if (emitter.isCancelled) {
                        return
                    }
                    emitter.onNext(data)
                }
                emitter.onComplete()
                // onCompleteの後に処理はするべきではない
            }
        }, BackpressureStrategy.BUFFER)// 通知できるまで待つデータ保持して待つ

        // Subscriber
        flowable
            .observeOn(Schedulers.computation()) // 処理を別スレッドで
            .subscribe(object : Subscriber<String>{
                lateinit var subscription : Subscription
                override fun onSubscribe(s: Subscription?) {
                    if (s != null) { // ---Ask---
                        this.subscription = s
                    }
                    // リクエストは最後にする
                    // 特に処理のスピードに問題がない場合は、Long.MAX_VALUEで制限なく通知を受けたほうがいい
                    this.subscription.request(1L)
                }

                override fun onNext(t: String?) {
                    var threadName = Thread.currentThread().name
                    println(threadName + t)
                    this.subscription.request(1L) // 次のデータをリクエスト
                }

                override fun onComplete() {
                    var threadName = Thread.currentThread().name
                    println(threadName + "完了")
                }

                override fun onError(t: Throwable?) {
                    t?.printStackTrace()
                }
            })

    }

}