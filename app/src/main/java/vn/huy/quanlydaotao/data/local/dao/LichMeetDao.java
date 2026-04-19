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
    @Query("SELECT * FROM lich_meet")
    LiveData<List<LichMeetEntity>> getAllLichMeet();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LichMeetEntity> lichMeets);
}