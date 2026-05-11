package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;
import vn.huy.quanlydaotao.domain.repository.INguoiDungRepository;

public class SuaThongTinUserUseCase {
    private final INguoiDungRepository repository;

    public SuaThongTinUserUseCase(INguoiDungRepository repository) {
        this.repository = repository;
    }

    public LiveData<NguoiDungResponse> thucHien(int id, String hoTen, String email) {
        return repository.suaThongTin(id, hoTen, email);
    }
}