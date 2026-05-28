package vn.huy.quanlydaotao.data.remote.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.huy.quanlydaotao.data.remote.dto.BaiKiemTraResponse;
import vn.huy.quanlydaotao.data.remote.dto.BaiHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.BaseResponse;
import vn.huy.quanlydaotao.data.remote.dto.CauHoiResponse;
import vn.huy.quanlydaotao.data.remote.dto.ChiTietKetQuaResponse;
import vn.huy.quanlydaotao.data.remote.dto.DaDocRequest;
import vn.huy.quanlydaotao.data.remote.dto.DaDocResponse;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopRequest;
import vn.huy.quanlydaotao.data.remote.dto.DangKyLopResponse;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassRequest;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;
import vn.huy.quanlydaotao.data.remote.dto.KetQuaRequest;
import vn.huy.quanlydaotao.data.remote.dto.KetQuaResponse;
import vn.huy.quanlydaotao.data.remote.dto.KhoaHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.LichMeetResponse;
import vn.huy.quanlydaotao.data.remote.dto.LichSuResponse;
import vn.huy.quanlydaotao.data.remote.dto.LopHocResponse;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungRequest;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;
import vn.huy.quanlydaotao.data.remote.dto.ThongBaoRequest;
import vn.huy.quanlydaotao.data.remote.dto.ThongBaoResponse;
import vn.huy.quanlydaotao.data.remote.dto.TienDoRequest;
import vn.huy.quanlydaotao.data.remote.dto.TienDoResponse;

public interface DichVuApi {
    @GET("api_khoa_hoc.php")
    Call<List<KhoaHocResponse>> getDanhSachKhoaHoc();

    @GET("api_lophoc.php")
    Call<LopHocResponse> getDanhSachLopHoc(
            @Query("id_khoa_hoc") int idKhoaHoc,
            @Query("id_nguoi_dung") int idNguoiDung
    );

    @GET("lay_bai_hoc.php")
    Call<List<BaiHocResponse>> getDanhSachBaiHoc(
            @Query("id_khoa_hoc") int idKhoaHoc,
            @Query("id_nguoi_dung") int idNguoiDung
    );
    @GET("lay_lich_hoc.php")
    Call<LichMeetResponse> getLichMeetDaDangKy(@Query("id_nguoi_dung") int idNguoiDung);
    @POST("dang_ky_lop.php")
    Call<DangKyLopResponse> dangKyLopHoc(@Body DangKyLopRequest request);
    @GET("ds_kiem_tra.php")
    Call<List<BaiKiemTraResponse>> getDanhSachBaiKiemTra(
            @Query("id_nguoi_dung") int idNguoiDung
    );
    @GET("ds_cau_hoi.php")
    Call<List<CauHoiResponse>> getDanhSachCauHoi(
            @Query("id_nguoi_dung") int idNguoiDung,
            @Query("id_bai_kiem_tra") int idBaiKiemTra
    );
    @POST("tra_ket_qua.php")
    Call<KetQuaResponse> guiBaiLam(@Body KetQuaRequest request);
    @GET("get_ket_qua.php")
    Call<ChiTietKetQuaResponse> layChiTietKetQua(@Query("id_ket_qua") int idKetQua);
    @FormUrlEncoded
    @POST("luu_fcm_token.php")
    Call<BaseResponse> luuFcmToken(
            @Field("id_nguoi_dung") int idNguoiDung,
            @Field("fcm_token") String fcmToken
    );
    @POST("sua_thong_tin.php")
    Call<NguoiDungResponse> suaThongTin(@Body NguoiDungRequest request);
    @POST("doi_pass.php")
    Call<DoiPassResponse> doiMatKhau(@Body DoiPassRequest request);
    @POST("lay_thong_bao.php")
    Call<ThongBaoResponse> layThongBao(@Body ThongBaoRequest request);
    @POST("cap_nhat_tien_do.php")
    Call<TienDoResponse> luuTienDo(@Body TienDoRequest request);
    @POST("da_doc.php")
    Call<DaDocResponse> danhDauDaDoc(@Body DaDocRequest request);
    @POST("xem_lich_su.php")
    Call<LichSuResponse> xemLichSuKiemTra(@Body Map<String, Integer> requestBody);
}