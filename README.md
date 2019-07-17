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
根目录的build.gradle加入
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
modul中
```groovy
implementation 'com.github.moonlight920:SmartTemplate:1.1.1'
```

## 使用
在androidmanifest文件中，application的theme需要继承`SampleAppTheme`
例如android:theme="@style/AppTheme"
```xml
<style name="AppTheme" parent="SampleAppTheme"/>
```
或者直接使用`SampleAppTheme`主题
```xml
<application
            android:name=".sample.MyApplication"
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/SampleAppTheme"
            tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".sample.Sample1Activity">
        </activity>
        <activity android:name=".sample.MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>
```
MyApplication是自定义的application
```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 可选，定义一个抽屉控件，可从左边拉出来
        val drawer = SlidingDrawer().apply {
            item {
                title = "execute"
                execute {
                    // ...
                }
            }
            item {
                title = "document"
                // 可以打开一个asset下的markdown文件                
                markdownAssetFileName = "README.md"
            }
            item {
	    	groupId = 1
		iconRes = R.drawable.logo_small
                title = "replace fragment"
                // 可以在主界面加载一个fragment
                openClazz = Sample1Fragment::class.java
            }
        }
        // 没有drawer可以不传
        SmartTemplate.init(this, drawer) {
            // 可打开一个activity
            activityItem(Sample1Activity::class.java) {
                title = "activity title 1"
                desc = "activity desc_1"
            }
            // 使用fragment替换主内容区
            fragmentItem(Sample1Fragment::class.java) {
                // ...
            }
            // 直接运行`execute`中的代码
            executionItem {
                // ...
            }
            // 嵌套列表，点击进入下一级
            itemList {
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
