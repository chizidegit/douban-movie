package com.aaron.doubanmovie.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Chenll on 2016/10/13.
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
