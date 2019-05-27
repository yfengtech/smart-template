# SmartTemplate
是一个写demo的壳子，用于快捷生成demo应用

你不需要重复的写的DemoActivity，不需要重复的画UI演示demo

此库支持打开activity，fragment或直接运行代码(用于调试dialog等功能)

引入此库，节省时间，只需要写你关心的东西

![演示](https://raw.githubusercontent.com/moonlight920/SmartTemplate/b5cc7ff0634a4001da57bf1c330de32af982485f/album/samrt_template.gif)  

[点此下载apk](https://raw.githubusercontent.com/moonlight920/SmartTemplate/master/app-debug.apk)

## 前提
使用此库，需注意如下
* `minSdkVersion` ≥ 21
* launcherActivity需要继承`AppCompatActivity`
* `compileSdkVersion`和`targetSdkVersion`最好为28
* 自定义一个application

## 引入
```groovy
implementation 'com.github.moonlight920:SmartTemplate:1.0.1'
```

## 使用
自定义的application即可
```kotlin
@Document("README.md") // 可选，此为项目中assets中的文件
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            // 可打开一个activity
            activityItem(Sample1Activity::class.java) {
                title = "activity title 1"
                desc = "activity desc_1"
            }
            // 使用fragment替换主内容区
            fragmentItem(Sample1Fragment::class.java) {
                title = "fragment title"
                desc = "fragment desc"
            }
            // 直接运行`execute`中的代码
            executionItem {
                title = "dialog title"
                desc = "dialog desc"
                execute {
                    val dialog = AlertDialog.Builder(it).create()
                    dialog.setTitle("我是title")
                    dialog.setMessage("我是message")
                    dialog.show()
                }
            }
            // 嵌套列表，点击进入下一级
            itemList {
                title = "list title"
                desc = "list desc"

                //可嵌套加入activityItem/executionItem/itemList
                activityItem{
                    //...
                }
                itemList{
                    //...
                }
            }


        }
    }
}
```
