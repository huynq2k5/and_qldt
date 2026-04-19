package vn.huy.quanlydaotao.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import vn.huy.quanlydaotao.data.local.entity.DangKyLopEntity;

@Dao
public interface DangKyLopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DangKyLopEntity dangKyLop);

    @Query("SELECT EXISTS(SELECT * FROM dang_ky_lop WHERE id_nguoi_dung = :idNguoiDung AND id_lop_hoc = :idLopHoc)")
    boolean isDaDangKy(int idNguoiDung, int idLopHoc);

    @Query("SELECT * FROM dang_ky_lop WHERE id_nguoi_dung = :idNguoiDung")
    List<DangKyLopEntity> getLopDaDangKy(int idNguoiDung);

    @Query("DELETE FROM dang_ky_lop WHERE id_lop_hoc = :idLopHoc")
    void deleteByLopHoc(int idLopHoc);
}