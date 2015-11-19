package com.example.codingchallenge;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationModule module = new ApplicationModule(this);

        getComponent(DaggerApplicationComponent.builder()
                .applicationModule(module)
                .build());

        getComponent().inject(this);
    }

    private ApplicationComponent applicationComponent;

    public ApplicationComponent getComponent() {
        return this.applicationComponent;
    }

    public void getComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
