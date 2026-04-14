package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.BaiHocEntity;

@Dao
public interface BaiHocDao {
    @Query("SELECT * FROM bai_hoc WHERE id_khoa_hoc = :idKhoaHoc ORDER BY id ASC")
    LiveData<List<BaiHocEntity>> getBaiHocByKhoaHoc(int idKhoaHoc);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BaiHocEntity> baiHocs);
}