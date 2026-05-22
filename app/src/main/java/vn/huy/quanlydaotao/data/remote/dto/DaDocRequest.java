package vn.huy.quanlydaotao.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class DaDocRequest {
    @SerializedName("id")
    private int id;

    public DaDocRequest(int id) {
        this.id = id;
    }
}