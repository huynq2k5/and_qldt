package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import vn.huy.quanlydaotao.data.local.entity.KetQuaEntity;

@Dao
public interface KetQuaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KetQuaEntity entity);

    @Query("SELECT * FROM ket_qua_local WHERE idKetQua = :id")
    LiveData<KetQuaEntity> getKetQuaById(int id);
}