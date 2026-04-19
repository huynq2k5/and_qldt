package vn.huy.quanlydaotao.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import vn.huy.quanlydaotao.domain.model.LichMeet;
import vn.huy.quanlydaotao.domain.repository.ILichMeetRepository;

public class LayDanhSachLichMeetUseCase {
    private ILichMeetRepository repository;
    public LayDanhSachLichMeetUseCase(ILichMeetRepository repository) {
        this.repository = repository;
    }
    public LiveData<List<LichMeet>> execute(int idLopHoc) {
        return repository.getDanhSachLichMeet(idLopHoc);
    }

    public void refresh(int idLopHoc) { repository.lamMoiLichMeet(idLopHoc); }
}
