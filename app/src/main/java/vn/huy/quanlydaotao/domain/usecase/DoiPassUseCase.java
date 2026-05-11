package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassRequest;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;
import vn.huy.quanlydaotao.domain.repository.IDoiPassRepository;

public class DoiPassUseCase {
    private final IDoiPassRepository repository;

    public DoiPassUseCase(IDoiPassRepository repository) {
        this.repository = repository;
    }

    public LiveData<DoiPassResponse> thucHien(int id, String passCu, String passMoi) {
        DoiPassRequest request = new DoiPassRequest(id, passCu, passMoi);
        return repository.doiMatKhau(request);
    }
}