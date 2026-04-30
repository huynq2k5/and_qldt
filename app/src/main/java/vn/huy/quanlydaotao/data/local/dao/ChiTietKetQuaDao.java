package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import vn.huy.quanlydaotao.data.local.entity.ChiTietKetQuaEntity;

@Dao
public interface ChiTietKetQuaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChiTietKetQuaEntity entity);

    @Query("SELECT * FROM chi_tiet_ket_qua_local WHERE idKetQua = :id")
    LiveData<ChiTietKetQuaEntity> getChiTietById(int id);
}