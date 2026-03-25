package vn.huy.quanlydaotao.data.repository;

public interface DataCallback {
    void onDataLoaded();
    void onError(String message);
}