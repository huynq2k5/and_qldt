package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.huy.quanlydaotao.data.local.entity.LopHocEntity;

@Dao
public interface LopHocDao {
    @Query("SELECT * FROM lop_hoc WHERE idKhoaHoc = :idKhoaHoc")
    LiveData<List<LopHocEntity>> layDanhSachLopHocTheoKhoaHoc(int idKhoaHoc);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void luuDanhSachLopHoc(List<LopHocEntity> danhSachLopHoc);
}
