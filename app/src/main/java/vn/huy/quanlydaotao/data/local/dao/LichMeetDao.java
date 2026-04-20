package vn.huy.quanlydaotao.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import vn.huy.quanlydaotao.data.local.entity.LichMeetEntity;

@Dao
public interface LichMeetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LichMeetEntity> lichMeets);

    @Query("SELECT * FROM lich_meet ORDER BY thoi_gian ASC")
    LiveData<List<LichMeetEntity>> getAllLichHoc();

    @Query("DELETE FROM lich_meet")
    void deleteAll();
}