# RxjavaAndRetrofit
Rxjava
  				Android 观察者设计模式
其实就是一对一或一对多的依赖关系，Observable被观察者，Observer观察者
当一个对象（subject）的状态发送改变时，所以依赖于它的对象都得到通知并被自动更新。
对于android中的观察者模式最为简单的例子就是在按钮的点击事件，button就是一个被观察者，View.onClickListener是一个观察者，通过setonClickListener建立依赖关系  

在日常代码中也大量观察者模式的例子，adapter就是一个较为明显例子 	 
listview.setAdapter(adapter);

 	Rxjava 说白就是Android 观察者设计模式的扩展。
1、rxjava可以分为Observable (被观察者)、 Observer (观察者)、 subscribe (订阅)、事件和Observable 和 Observer 的依赖关系subscribe (由此就subscriber订阅者 )
2、rxjava事件的回调主要有 onNext() 、onCompleted() 和 onError()。onCompleted() 和onError()是互斥
Observable (被观察者),Observable (被观察者),subscriber订阅者,建立联系.
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
