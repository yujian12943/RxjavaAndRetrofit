# RxjavaAndRetrofit
Rxjava
Android 观察者设计模式
其实就是一对一或一对多的依赖关系，Observable被观察者，Observer观察者
当一个对象（subject）的状态发送改变时，所以依赖于它的对象都得到通知并被自动更新。
对于android中的观察者模式最为简单的例子就是在按钮的点击事件，button就是一个被观察者，View.onClickListener是一个观察者，通过setonClickListener建立依赖关系  
在日常代码中也大量观察者模式的例子，adapter就是一个较为明显例子 	 
listview.setAdapter(adapter);  
notifyDataSetChanged();   
Rxjava 说白就是Android 观察者设计模式的扩展。
1、rxjava可以分为Observable (被观察者)、 Observer (观察者)、 subscribe (订阅)、事件和Observable 和 Observer 的依赖关系subscribe (由此就subscriber订阅者 )
2、rxjava事件的回调主要有 onNext() 、onCompleted() 和 onError()。
onCompleted() 和onError()是互斥

Observable (被观察者),Observer (观察者),subscriber订阅者,建立联系

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
        
Rxjava  rx表示异步机制，所以就要实现异步回调机制，指定线程去进行工作，指定线程去回调机制。也就是Rxjava中的Scheduler 的机制
subscribeOn()指定在那个线程开始工作（一般为子线程耗时工作）也就是事件产生线程observeOn()指定回调线程，也就是最后执行消费的线程
Scheduler 中的API中有对线程的指定：Schedulers.immediate()（当前线程），Schedulers.newThread()（子线程），Schedulers.io()（子线程池），AndroidSchedulers.mainThread()（android主线程）。

          Observable.just(1,2,3,4
          .subscribeOn(Schedulers.io())
          .observeOn(Schedulers.immediate())
          .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                    }
                });
    observable快捷传入数据的方式just(),和from();出入的数据类型都为Object
    
Rxjava  变换，其实是Observable传入数据（from,just方法）经过数据处理最后给subscriber,Observer处理后的结果。
1、单个数据

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
2、循环数据

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
Rxjava是响应式编程
Observable和Subscriber可以做任何事情
例如：
Observable可以是一个数据库查询，Subscriber用来显示查询结果
Observable可以是屏幕上的点击事件，Subscriber用来响应点击事件
Observable可以是一个网络请求，Subscriber用来显示请求结果。

Rxjava  操作符，---真正牛逼的地方。
1、buffer操作符，是对获取的数据的数据源进行缓存，相隔一定时间后统一发送给Subscriber

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
    2、concatMap操作符，跟上面flatMap操作符类似，是把Observable产生的结果转换成多个Observable，
    然后把这多个Observable“扁平化”成一个Observable，并依次提交产生的结果给订阅者------不同是保证数据的顺序
     //concatMap操作符的运行结果10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
        Observable.just(10, 20, 30).concatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
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
  3、distinct操作符，filter操作符
过滤操作符
distinct操作符对源Observable产生的结果进行过滤，把重复的结果过滤掉
filter操作符是对源Observable产生的结果按照指定条件进行过滤

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

Rxjava操作符。
1、创建操作
用于创建Observable的操作符：create，Start，Timer等等，
2、变换操作
对Observable发射的数据进行变换：buffer,flatmap,window等
3、过滤操作
这些操作符用于从Observable发射的数据中进行选择：Distinct ，Filter，TakeLast，Last等
4、组合操作
组合操作符用于将多个Observable组合成一个单一的Observable：Join，Merge，Retry等
5、辅助操作
一组用于处理Observable的操作符，Delay，Do，Serialize，Timeout。
6、条件和布尔操作
这些操作符可用于单个或多个数据项，也可用于Observable：All,Contains 等
7、算术和聚合操作
这些操作符可用于整个数据序列，max,min,count,

8、连接操作
一些有精确可控的订阅行为的特殊Observable，
Connect — 指示一个可连接的Observable开始发射数据给订阅者。

9、转换操作
To — 将Observable转换为其它的对象或数据结构
Blocking 阻塞Observable的操作符

10、操作符决策树
对以上操作符进行组合嵌套操作Observable。

Retrofit是类型安全的REST安卓客户端请求库，内部是okhttp发送的网络请求，
Retrofit和Okhttp、Rxjava、gson是目前最好用的网络请求库。

    String baseUrl = "https://";
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieEntity> call = movieService.getNewData(0, 10);
        try {
            Response<MovieEntity> S=call.execute();//同步
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback<MovieEntity>() {//异步
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                text.setText(response.body().toString());
            }
            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                    text.setText(t.getMessage());
            }
        });
    }
    1、创建请求对象
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
           movieService = retrofit.create(MovieService.class);
       2、api请求服务
       
       @GET("get请求")
    Call<MovieEntity> getNewData(@Query("start")int start,@Query("count")int count);
    @POST("post")
    Call<MovieEntity> getpostData(@Query("start")int start,@Query("count")int count);
    @POST("上传文件")
    void upload(@Part("file") File file, Callback<File> callback);
//    @Path()---更换url参数
//    @Body-----提交实体
//    @Query()——————url自动拼参数
//    @Header--------url添加header信息
//    @FieldMap-----提交map表单
//    @Part----文件
