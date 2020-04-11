# SmartTemplate

> 仓库https://jitpack.io/

### 1.0.0

### 1.1.1

修复toolbar多层跳转之后，title显示的bug

### 1.1.2

minSdkVersion  21 --> 19

### 1.1.3

提供列表数据支持
提供方法，直接为RecyclerView生成数据

### 1.1.4

对列表数据，支持数量选择

## 更换仓库

> 更换仓库至yfengtech.cn

### 1.0.0

去掉了Anko库，去掉了drawer

### 1.0.1

将SampleContainer序列化传给Fragment，在保存fragment的state时，会引发writeToParcel的问题
所以传入之后，将其从arguments中移除
