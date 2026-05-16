package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.ThongBaoEntity;

@Dao
public interface ThongBaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ThongBaoEntity> thongBaos);

    @Query("SELECT * FROM thong_bao WHERE id_nguoi_dung = :idNguoiDung ORDER BY id DESC")
    LiveData<List<ThongBaoEntity>> getThongBaoByNguoiDung(int idNguoiDung);

    @Query("DELETE FROM thong_bao WHERE id_nguoi_dung = :idNguoiDung")
    void deleteByNguoiDung(int idNguoiDung);
}