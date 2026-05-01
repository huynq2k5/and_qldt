package vn.huy.quanlydaotao.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>(true);

    public void setNetworkStatus(boolean status) {
        isConnected.setValue(status);
    }

    public LiveData<Boolean> getIsConnected() {
        return isConnected;
    }
}