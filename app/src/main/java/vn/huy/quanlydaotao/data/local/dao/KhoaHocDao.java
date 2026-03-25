package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.KhoaHocEntity;

@Dao
public interface KhoaHocDao {
    @Query("SELECT * FROM khoa_hoc")
    LiveData<List<KhoaHocEntity>> getDanhSachKhoaHoc();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<KhoaHocEntity> khoaHocList);

    @Query("DELETE FROM khoa_hoc")
    void deleteAll();
}
