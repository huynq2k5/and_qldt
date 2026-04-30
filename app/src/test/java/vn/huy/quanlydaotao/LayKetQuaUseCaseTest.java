package vn.huy.quanlydaotao.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.domain.model.ChiTietKetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryChiTietKetQua;

public class LayKetQuaUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private IRepositoryChiTietKetQua mockRepository;
    private LayKetQuaUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(IRepositoryChiTietKetQua.class);
        useCase = new LayKetQuaUseCase(mockRepository);
    }

    @Test
    public void testExecute_ReturnsChiTietKetQua() {
        int idKetQua = 2026;

        List<ChiTietKetQua.CauHoiChiTiet> dummyDetails = new ArrayList<>();
        dummyDetails.add(new ChiTietKetQua.CauHoiChiTiet(
                1,
                "C",
                1,
                "Lớp nào dùng để lưu trữ dữ liệu cục bộ?",
                "Room Database"
        ));

        ChiTietKetQua expectedData = new ChiTietKetQua(
                8.5,
                "dat",
                "2026-04-30 15:30:00",
                dummyDetails
        );

        MutableLiveData<ChiTietKetQua> liveData = new MutableLiveData<>();
        liveData.setValue(expectedData);

        when(mockRepository.getChiTietKetQua(idKetQua)).thenReturn(liveData);

        useCase.execute(idKetQua).observeForever(result -> {
            assertNotNull(result);
            assertEquals(8.5, result.getDiemSo(), 0.01);
            assertEquals("dat", result.getTrangThai());
            assertEquals(1, result.getDanhSachChiTiet().size());

            ChiTietKetQua.CauHoiChiTiet detail = result.getDanhSachChiTiet().get(0);
            assertEquals("Room Database", detail.getDapAnDung());
            assertEquals("C", detail.getCauTraLoi());

            System.out.println("GET Flow Test PASSED: Dữ liệu chi tiết kết quả truy xuất chính xác.");
        });

        verify(mockRepository).getChiTietKetQua(idKetQua);
    }
}