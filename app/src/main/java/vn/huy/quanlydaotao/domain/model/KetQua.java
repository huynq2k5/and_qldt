package vn.huy.quanlydaotao.domain.model;
public class KetQua {
    private String status;
    private int idKetQua;

    public KetQua(String status, int idKetQua) {
        this.status = status;
        this.idKetQua = idKetQua;
    }

    public String getStatus() { return status; }
    public int getIdKetQua() { return idKetQua; }
}