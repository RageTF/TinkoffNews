package com.ragertf.tinkoffnews.di.components

import com.ragertf.tinkoffnews.di.modules.activity.NavigatorModule
import com.ragertf.tinkoffnews.di.scopes.ActivityScope
import com.ragertf.tinkoffnews.presentation.view.activity.MainActivity
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [NavigatorModule::class])
@ActivityScope
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}