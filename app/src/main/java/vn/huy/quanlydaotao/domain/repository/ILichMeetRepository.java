package vn.huy.quanlydaotao.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import vn.huy.quanlydaotao.domain.model.LichMeet;

public interface ILichMeetRepository {
    LiveData<List<LichMeet>> getDanhSachLichMeet(int idLopHoc);
    void lamMoiLichMeet(int idLopHoc);
}
