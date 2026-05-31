package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.ChungChiEntity;

@Dao
public interface ChungChiDao {
    @Query("SELECT * FROM chung_chi_local ORDER BY id DESC")
    LiveData<List<ChungChiEntity>> getDanhSachChungChiLocal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ChungChiEntity> chungChiList);

    @Query("DELETE FROM chung_chi_local")
    void deleteAll();
}