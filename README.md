# SmartTemplate
这个项目用来帮助你快速完成示例代码，只需要把你要跳转的界面或者要进行的操作进行一些简单的配置，我会帮你管理分组，添加描述。

一句话来说，这就是个写Demo的壳子。。。

如果想要了解本项目的设计  [点击查看](./document/DESIGN.md)
## 背景
在需要写大量的演示或测试项目时，频繁的创建项目，画界面，然后跳转  
或者需要一些数据时，就写一个循环造了一些假数据出来  
并且每次这样的示例代码完全没有条理，十分混乱

这个库的产出就是为了整理这些演示和示例代码，让其界面工整，逻辑清晰

## 功能
* 管理界面分组，支持Activity和Fragment的分组
* 支持直接打开activity，fragment或直接运行代码(用于调试dialog等功能)
* 提供一些常用功能，例如快速生成列表数据等


引入此库，节省时间，只需要写你关心的东西

## 演示

| 描述 | 代码 | 结果|
|---|---|---|
| 显示一个Fragment | ![](https://github.com/yfengtech/SmartTemplate/raw/master/album/code_fragment.png) |![](https://github.com/yfengtech/SmartTemplate/raw/master/album/fragment.gif)|
| 打开一个Activity | ![](https://github.com/yfengtech/SmartTemplate/raw/master/album/code_activity.png) |![](https://github.com/yfengtech/SmartTemplate/raw/master/album/activity.gif)|
| 执行代码块 | ![](https://github.com/yfengtech/SmartTemplate/raw/master/album/code_execute.png) |![](https://github.com/yfengtech/SmartTemplate/raw/master/album/execution.gif)|
| 分组显示 | ![](https://github.com/yfengtech/SmartTemplate/raw/master/album/code_itemlist.png) |![](https://github.com/yfengtech/SmartTemplate/raw/master/album/itemlist.gif)|


[点此下载apk体验](https://raw.githubusercontent.com/yfengtech/SmartTemplate/master/app-debug.apk)

## 引入
根目录的build.gradle加入

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        ...
    }
}
```
modul中

```groovy
implementation 'com.github.yfengtech:SmartTemplate:1.1.3'
```

## 配置
这个库使用Kotlin开发，使用时需要DSL配置，如果你不熟悉，没关系很简单，根据上面的示例可以轻易操作。（如果源码能给你带来一些帮助，我会很高兴😄）

Gradle配置如下：

* `minSdkVersion` ≥ 19
* 项目的启动Activity需要继承`AppCompatActivity`（内部会自动拦截，所以这个activity中什么都不需要写）
* 为了保证版本统一，少出错；`compileSdkVersion`和`targetSdkVersion`最好为28（否则可能会报资源找不到的错，如果你一定要低于28，将库中的`support`包排除）
* 建议将启动的Activity屏幕方向固定，防止在旋转的时候 UI错乱

## 使用

* 自定义一个application，例如

```kotlin
class MyApplication : Application() {
	override fun onCreate() {
		super.onCreate()
	}
}
```

* 在AndroidManifest中更换默认`android:theme`和`android:name`

```xml
<application
	android:name=".MyApplication"
	android:theme="@style/SampleAppTheme">
            
    ...
</application>
```

* 在Application直接配置要执行的操作

```kotlin
class MyApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            fragmentItem(Sample1Fragment::class.java) {
                title = "fragment title"
                desc = "fragment desc"
            }
            activityItem(Sample1Activity::class.java) {
                title = "Sample Activity"
                desc = "我是个activity"
            }
            executionItem {
                title = "弹出dialog"
                desc = "直接执行代码块，弹出dialog"
                execute { context ->
                    AlertDialog.Builder(context)
                        .setTitle("我是title")
                        .setMessage("我是message")
                        .create()
                        .show()
                }
            }
            itemList {
                title = "我是一个分组"
                fragmentItem(Sample1Fragment::class.java) {
                    title = "fragment title"
                    desc = "fragment desc"
                }
                activityItem(Sample1Activity::class.java) {
                    title = "Sample Activity"
                    desc = "我是个activity"
                }
            }
        }
    }
}
```

## 其他功能

后期增加了一些便捷的辅助功能

### 快速生成列表数据

调用这个函数，接受一个RecyclerView，直接帮你填充数据

* orientation 列表方向(默认竖向)
* itemCount：需要的item数量
* useEnglishText：true为英文数据，false为中文数据（默认true）

```kotlin
fun generateSampleData(
        context: Context,
        recyclerView: RecyclerView,
        orientation: Int = LinearLayoutManager.VERTICAL,
        itemCount: Int? = null,
        useEnglishText: Boolean = true
    )
```

调用

```kotlin
SmartTemplate.generateSampleData(context, recyclerView)
```

#### 示例
![](https://github.com/yfengtech/SmartTemplate/raw/master/album/create_list.gif)

## 最后
* 如果你觉得还不错，欢迎Star  
* 如果使用过程中发现问题，欢迎Issues  

> 这个库本人一直在使用，会持续维护下去


