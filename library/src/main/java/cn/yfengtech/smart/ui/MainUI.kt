package cn.yfengtech.smart.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yf.smarttemplate.R
import cn.yfengtech.smart.fragment.popFragment
import cn.yfengtech.smart.sample.SampleContainer

/**
 * Created by yf.
 * @date 2019-06-02
 */
object MainUI {
    /**
     * app名称
     */
    internal var appTitle = "origin"

    /**
     * 原始模板入口
     */
    internal lateinit var originTemplateContainer: SampleContainer
}

/**
 * 替换activity内的布局，
 */
internal fun AppCompatActivity.replaceContentView() {
    setContentView(R.layout.layout_main)
    val toolbar = findViewById<Toolbar>(R.id.toolBar)
    initToolbar(toolbar)
}

internal fun AppCompatActivity.initToolbar(toolbar: Toolbar) {
    toolbar.title = MainUI.appTitle
    setSupportActionBar(toolbar)
    // 返回按钮点击事件
    toolbar.setNavigationOnClickListener {
        popFragment()
    }
}


/**
 * 设置toolbar中返回键的显示和隐藏
 * fragment栈中count大于0时才显示，否则认为是在首页
 */
internal fun AppCompatActivity.setToolbarBackListener() {
    supportFragmentManager.addOnBackStackChangedListener {
        val stackCount = supportFragmentManager.backStackEntryCount
        setActionBarBackShow(stackCount > 0)
    }
}


/**
 * 设置ActionBar的返回按钮是否显示
 */
internal fun AppCompatActivity.setActionBarBackShow(isShow: Boolean) {
    supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
    supportActionBar?.setHomeButtonEnabled(isShow)
}


