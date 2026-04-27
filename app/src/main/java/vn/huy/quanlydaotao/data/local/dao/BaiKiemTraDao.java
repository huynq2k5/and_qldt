package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.BaiKiemTraEntity;

@Dao
public interface BaiKiemTraDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BaiKiemTraEntity> list);

    @Query("SELECT * FROM bai_kiem_tra ORDER BY id DESC")
    LiveData<List<BaiKiemTraEntity>> getAll();

    @Query("DELETE FROM bai_kiem_tra")
    void deleteAll();
}