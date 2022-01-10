package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Observable.fromArray("test", "tanvi", "Rob")
//            .subscribeOn(Schedulers.newThread())
//            .filter { item -> item == "tanvi" }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { onNext -> Toast.makeText(this, onNext, Toast.LENGTH_SHORT).show() }

        getTextFromNetwork()
    }


    fun getTextFromNetwork() {
        Observable.create<String> { subscriber ->
            val net = NetworkOperation()
            try {
                subscriber.onNext(net.getResult())
                subscriber.onComplete()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object  : Observer<String?>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(onNext: String) {
                    Toast.makeText(this@MainActivity, onNext, Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {

                }

            })
    }

    class NetworkOperation {
        fun getResult(): String {
            return "result"
        }
    }
}