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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LopHocEntity> lopHocs);

    @Query("SELECT * FROM lop_hoc WHERE id_khoa_hoc = :idKhoaHoc")
    LiveData<List<LopHocEntity>> getLopHocByKhoaHoc(int idKhoaHoc);

    @Query("DELETE FROM lop_hoc WHERE id_khoa_hoc = :idKhoaHoc")
    void deleteByKhoaHoc(int idKhoaHoc);
}