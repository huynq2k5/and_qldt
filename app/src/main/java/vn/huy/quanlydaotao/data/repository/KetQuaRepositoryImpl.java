package vn.huy.quanlydaotao.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.huy.quanlydaotao.data.local.dao.KetQuaDao;
import vn.huy.quanlydaotao.data.local.entity.KetQuaEntity;
import vn.huy.quanlydaotao.data.remote.api.DichVuApi;
import vn.huy.quanlydaotao.data.remote.dto.KetQuaRequest;
import vn.huy.quanlydaotao.data.remote.dto.KetQuaResponse;
import vn.huy.quanlydaotao.domain.model.KetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryKetQua;

public class KetQuaRepositoryImpl implements IRepositoryKetQua {
    private final KetQuaDao ketQuaDao;
    private final DichVuApi dichVuApi;

    public KetQuaRepositoryImpl(KetQuaDao ketQuaDao, DichVuApi dichVuApi) {
        this.ketQuaDao = ketQuaDao;
        this.dichVuApi = dichVuApi;
    }

    @Override
    public LiveData<KetQua> nopBai(KetQuaRequest request) {
        MutableLiveData<KetQua> liveData = new MutableLiveData<>();

        dichVuApi.guiBaiLam(request).enqueue(new Callback<KetQuaResponse>() {
            @Override
            public void onResponse(Call<KetQuaResponse> call, Response<KetQuaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    KetQuaResponse res = response.body();
                    KetQua domainModel = new KetQua(res.getStatus(), res.getId_ket_qua());

                    new Thread(() -> {
                        ketQuaDao.insert(new KetQuaEntity(res.getId_ket_qua(), res.getStatus()));
                        liveData.postValue(domainModel);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<KetQuaResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }
}