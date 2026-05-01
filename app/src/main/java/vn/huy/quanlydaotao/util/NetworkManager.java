package vn.huy.quanlydaotao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;

public class NetworkManager {

    public interface OnNetworkChangeListener {
        void onChange(boolean isConnected);
    }

    private final ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private final OnNetworkChangeListener listener;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public NetworkManager(Context context, OnNetworkChangeListener listener) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.listener = listener;
    }

    public void startMonitoring() {
        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                mainHandler.post(() -> listener.onChange(true));
            }

            @Override
            public void onLost(@NonNull Network network) {
                mainHandler.post(() -> listener.onChange(false));
            }
        };

        connectivityManager.registerNetworkCallback(request, networkCallback);
        listener.onChange(checkCurrentNetwork());
    }

    public void stopMonitoring() {
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private boolean checkCurrentNetwork() {
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) return false;
        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
        return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}