package vn.huy.quanlydaotao.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import vn.huy.quanlydaotao.data.local.entity.BaiLamTamEntity;

@Dao
public interface BaiLamTamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void luuCauTraLoi(BaiLamTamEntity entity);

    @Query("SELECT * FROM bai_lam_tam")
    List<BaiLamTamEntity> layToanBoBaiLam();

    @Query("SELECT cauTraLoi FROM bai_lam_tam WHERE idCauHoi = :id")
    String layCauTraLoiTheoId(int id);

    @Query("DELETE FROM bai_lam_tam")
    void xoaSachBaiLam();
}