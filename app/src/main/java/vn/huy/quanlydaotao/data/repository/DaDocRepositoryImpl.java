package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.DaDocRequest;
import vn.huy.quanlydaotao.data.remote.dto.DaDocResponse;
import vn.huy.quanlydaotao.domain.repository.IRepositoryDaDoc;

public class DaDocRepositoryImpl implements IRepositoryDaDoc {
    private final DichVuApi dichVuApi;

    public DaDocRepositoryImpl(DichVuApi dichVuApi) {
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<DaDocResponse> danhDauDaDoc(int id) {
        MutableLiveData<DaDocResponse> liveData = new MutableLiveData<>();
        DaDocRequest request = new DaDocRequest(id);

        dichVuApi.danhDauDaDoc(request).enqueue(new Callback<DaDocResponse>() {
            @Override
            public void onResponse(Call<DaDocResponse> call, Response<DaDocResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<DaDocResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });

        return liveData;
    }
}