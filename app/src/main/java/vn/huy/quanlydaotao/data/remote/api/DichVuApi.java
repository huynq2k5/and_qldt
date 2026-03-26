package vn.huy.quanlydaotao.data.remote.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.huy.quanlydaotao.data.remote.dto.KhoaHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.LopHocResponse;

public interface DichVuApi {
    @GET("api_khoa_hoc.php")
    Call<List<KhoaHocResponse>> getDanhSachKhoaHoc();

    @GET("api_lophoc.php")
    Call<List<LopHocResponse>> getDanhSachLopHoc(@Query("id_khoa_hoc") int idKhoaHoc);
}
