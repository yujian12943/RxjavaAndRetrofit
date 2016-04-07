package com.java.yu.rxjavaandretrofit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TestActvity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private  void setTest(){
        //观察者
        Observer<String> observer= new Observer<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String s) {
            }
        };
        //订阅者
        Subscriber<String> subscriber= new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String s) {
                System.out.print("s:;"+s);
            }
        };
        //释放资源
        subscriber.unsubscribe();

        //被观察者
        Observable observable= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onCompleted();
            }
        });
        //建立联系
        observable.subscribe(subscriber);

    }
    private void setTest2(){
        String[] names={"111","1111","333"};
        ArrayList<Map<String,String>> list_map= new ArrayList<Map<String,String>>();
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
        Observable.from(list_map).subscribe(new Action1<Map<String, String>>() {
            @Override
            public void call(Map<String, String> stringStringMap) {

            }
        });
        Observable.just(1,2,3,4).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                });
    }
    private void setTest3(){

        Observable.just("image/log.png").map(new Func1<String, Object>() {
            @Override
            public Object call(String s) {
                return null;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        });


        Observable.just("image/log.png").concatMap(new Func1<String, Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call(String s) {
                return null;
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap o) {

            }
        });
        Observable.just("image/log.png").flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return null;
            }
        }).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {

            }
        });

        Map<String,ArrayList<String>> map= new HashMap<String,ArrayList<String>>();
        ArrayList<String> list_map= new ArrayList<String>();
        Observable.just(map).concatMap(new Func1<Map<String, ArrayList<String>>, Observable<?>>() {
            @Override
            public Observable<?> call(Map<String, ArrayList<String>> stringArrayListMap) {
                return Observable.from(stringArrayListMap.get(""));
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object o) {

            }
        });

    }
//    private void setTest4(){
//        Observable.mergeDelayError(
//                //在新线程中加载本地缓存图片
//                loadBitmapFromLocal().subscribeOn(Schedulers.io()),
//                //在新线程中加载网络图片
//                loadBitmapFromNet().subscribeOn(Schedulers.newThread()),
//                Observable.timer(3, TimeUnit.SECONDS).map(c->null))
//                //每隔2秒获取加载数据
//                .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .flatMap(r -> {
//                    if (r == null)  //如果没有获取到图片，直接跳转到主页面
//                        return Observable.empty();
//                    else { //如果获取到图片，则停留2秒再跳转到主页面
//                        view.setImageDrawable(r);
//                        return Observable.timer(2, TimeUnit.SECONDS);
//                    }
//                }).subscribe(
//                r->{
//                },
//                e->{
//                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                    finish();
//                },
//                ()->{
//                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                    finish();
//                }
//        );
//    }
//    protected void onCreate2(Bundle savedInstanceState) {
//
//
//        Observable.mergeDelayError(
//                //在新线程中加载本地缓存图片
//                loadBitmapFromLocal().subscribeOn(Schedulers.io()),
//                //在新线程中加载网络图片
//                loadBitmapFromNet().subscribeOn(Schedulers.newThread()),
//                Observable.timer(3,TimeUnit.SECONDS).map(c->null))
//                //每隔2秒获取加载数据
//                .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .flatMap(r->{
//                    if(r==null)  //如果没有获取到图片，直接跳转到主页面
//                        return Observable.empty();
//                    else { //如果获取到图片，则停留2秒再跳转到主页面
//                        view.setImageDrawable(r);
//                        return Observable.timer(2, TimeUnit.SECONDS);
//                    }
//                }).subscribe(
//                r->{
//                },
//                e->{
//                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                    finish();
//                },
//                ()->{
//                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                    finish();
//                }
//        );
//    }

    private void setTest5(){

        Observable<String> observable= Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (subscriber.isUnsubscribed())
                    return;
                try {
                    while (true) {
                        subscriber.onNext("1222");
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
        observable.buffer(3, TimeUnit.SECONDS).subscribe(new Action1<List<String>>() {
            @Override
            public void call(List<String> strings) {

            }
        });

        //concatMap操作符的运行结果
        Observable.just(10, 20, 30).concatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("concatMap Next:" + integer);
            }
        });

        Observable.just(1, 2, 1, 1, 2, 3)
                .distinct()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println(":::" + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                    }

                    @Override
                    public void onCompleted() {
                    }
                });

        Observable.just(1, 2, 3, 4, 5)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer item) {
                        return( item < 4 );
                    }
                }).subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println(":::: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                    }
                    @Override
                    public void onCompleted() {
                    }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
