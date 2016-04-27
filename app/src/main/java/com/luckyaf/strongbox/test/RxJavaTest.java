package com.luckyaf.strongbox.test;

import com.luckyaf.strongbox.util.AESUtils;
import com.luckyaf.strongbox.util.image.ImageInformation;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/4/25
 */
public class RxJavaTest {
    public static void main(String args[]) {
        final String content = "test hahahahhahahah";
        ImageInformation imageInformation = new ImageInformation("test");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String after = AESUtils.decode(content);
                subscriber.onNext(after);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onNext(String after) {
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
