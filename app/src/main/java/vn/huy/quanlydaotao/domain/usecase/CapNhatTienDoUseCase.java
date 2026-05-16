package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.TienDoResponse;
import vn.huy.quanlydaotao.domain.repository.ITienDoRepository;

public class CapNhatTienDoUseCase {

    private final ITienDoRepository repository;

    public CapNhatTienDoUseCase(ITienDoRepository repository) {
        this.repository = repository;
    }

    public LiveData<TienDoResponse> thucHienCapNhat(int idNguoiDung, int idBaiHoc, int phanTram) {
        return repository.luuTienDoRemote(idNguoiDung, idBaiHoc, phanTram);
    }
}