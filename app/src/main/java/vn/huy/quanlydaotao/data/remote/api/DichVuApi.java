package vn.huy.quanlydaotao.data.remote.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.huy.quanlydaotao.data.remote.dto.BaiKiemTraResponse;
import vn.huy.quanlydaotao.data.remote.dto.BaiHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopRequest;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.data.remote.dto.KhoaHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.LichMeetResponse;
import vn.huy.quanlydaotao.data.remote.dto.LopHocResponse;

public interface DichVuApi {
    @GET("api_khoa_hoc.php")
    Call<List<KhoaHocResponse>> getDanhSachKhoaHoc();

    @GET("api_lophoc.php")
    Call<LopHocResponse> getDanhSachLopHoc(
            @Query("id_khoa_hoc") int idKhoaHoc,
            @Query("id_nguoi_dung") int idNguoiDung
    );

    @GET("lay_bai_hoc.php")
    Call<List<BaiHocResponse>> getDanhSachBaiHoc(@Query("id_khoa_hoc") int idKhoaHoc);
    @GET("lay_lich_hoc.php")
    Call<LichMeetResponse> getLichMeetDaDangKy(@Query("id_nguoi_dung") int idNguoiDung);
    @POST("dang_ky_lop.php")
    Call<DangKyLopResponse> dangKyLopHoc(@Body DangKyLopRequest request);
    @GET("ds_kiem_tra.php")
    Call<List<BaiKiemTraResponse>> getDanhSachBaiKiemTra(
            @Query("id_nguoi_dung") int idNguoiDung
    );
}