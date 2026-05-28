package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.XoaDangKyRequest;
import vn.huy.quanlydaotao.data.remote.dto.XoaDangKyResponse;
import vn.huy.quanlydaotao.domain.repository.IRepositoryXoaDangKy;

public class XoaDangKyRepositoryImpl implements IRepositoryXoaDangKy {

    private final DichVuApi api;

    public XoaDangKyRepositoryImpl(DichVuApi api) {
        this.api = api;
    }

    @Override
    public LiveData<XoaDangKyResponse> xoaDangKyRemote(int idNguoiDung, int idLopHoc) {
        MutableLiveData<XoaDangKyResponse> liveData = new MutableLiveData<>();
        XoaDangKyRequest request = new XoaDangKyRequest(idNguoiDung, idLopHoc);

        api.xoaDangKyLopHoc(request).enqueue(new Callback<XoaDangKyResponse>() {
            @Override
            public void onResponse(Call<XoaDangKyResponse> call, Response<XoaDangKyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<XoaDangKyResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });

        return liveData;
    }
}