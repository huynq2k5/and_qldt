package vn.huy.quanlydaotao.data.remote.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import vn.huy.quanlydaotao.data.remote.dto.KhoaHocResponse;

public interface DichVuApi {
    @GET("api_khoa_hoc.php")
    Call<List<KhoaHocResponse>> getDanhSachKhoaHoc();
}
