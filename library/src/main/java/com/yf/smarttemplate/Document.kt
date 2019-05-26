package com.yf.smarttemplate

/**
 * Created by yf.
 * @date 2019-05-26
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Document(val value: String)