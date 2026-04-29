package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.huy.quanlydaotao.data.local.entity.CauHoiEntity;

@Dao
public interface CauHoiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CauHoiEntity> list);

    @Query("SELECT * FROM cau_hoi WHERE idBaiKiemTra = :idBkt ORDER BY id ASC")
    LiveData<List<CauHoiEntity>> getCauHoiByBaiKiemTra(int idBkt);

    @Query("DELETE FROM cau_hoi WHERE idBaiKiemTra = :idBkt")
    void deleteAllByBaiKiemTra(int idBkt);
}