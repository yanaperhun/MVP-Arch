package com.qati.cats;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.qati.cats.di.ComponentManager;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class App extends Application {

    private static ComponentManager daggerComponentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        daggerComponentManager = new ComponentManager();
        daggerComponentManager.init(this);

//        initializeSSLContext(this);
//        new GlideBuilder().setConnectivityMonitorFactory(new ConnectivityMonitorFactory() {
//            @NonNull
//            @Override
//            public ConnectivityMonitor build(@NonNull Context context, @NonNull ConnectivityMonitor.ConnectivityListener listener) {
//                return null;
//            }
//        })
    }


    public static ComponentManager getDagger() {
        return daggerComponentManager;
    }
    /**
     * Initialize SSL
     * @param mContext
     */
//    public static void initializeSSLContext(Context mContext){
//        if (Build.VERSION.SDK_INT == 19) {
//            try {
//                ProviderInstaller.installIfNeeded(this);
//            } catch (Exception ignored) {
//            }
//        }
//    }
}
