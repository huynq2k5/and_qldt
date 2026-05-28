package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.LichSuEntity;

@Dao
public interface LichSuDao {
    @Query("SELECT * FROM ket_qua_kiem_tra WHERE id_nguoi_dung = :idNguoiDung ORDER BY thoi_gian_nop DESC")
    LiveData<List<LichSuEntity>> getLichSuByNguoiDung(int idNguoiDung);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LichSuEntity> entities);

    @Query("DELETE FROM ket_qua_kiem_tra WHERE id_nguoi_dung = :idNguoiDung")
    void deleteByNguoiDung(int idNguoiDung);
}