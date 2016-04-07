# RxjavaAndRetrofit
Rxjava
  				Android 观察者设计模式
其实就是一对一或一对多的依赖关系，Observable被观察者，Observer观察者
当一个对象（subject）的状态发送改变时，所以依赖于它的对象都得到通知并被自动更新。
对于android中的观察者模式最为简单的例子就是在按钮的点击事件，button就是一个被观察者，View.onClickListener是一个观察者，通过setonClickListener建立依赖关系  
