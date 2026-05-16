package vn.huy.quanlydaotao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
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

import vn.huy.quanlydaotao.domain.model.ThongBao;
import vn.huy.quanlydaotao.domain.repository.IRepositoryThongBao;
import vn.huy.quanlydaotao.domain.usecase.LayDanhSachThongBaoUseCase;

public class LayDanhSachThongBaoUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private IRepositoryThongBao mockRepository;
    private LayDanhSachThongBaoUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(IRepositoryThongBao.class);
        useCase = new LayDanhSachThongBaoUseCase(mockRepository);
    }

    @Test
    public void testLayDanhSachThongBao_ReturnsListFromLocalAfterSync() {
        int idNguoiDung = 23004224;

        List<ThongBao> danhSachGiaDinh = new ArrayList<>();
        danhSachGiaDinh.add(new ThongBao(1, idNguoiDung, "Thông báo học tập", "Lớp học sắp bắt đầu", "2026-05-16 09:00:00", 0));
        danhSachGiaDinh.add(new ThongBao(2, idNguoiDung, "Thông báo hệ thống", "Cập nhật ứng dụng thành công", "2026-05-15 18:30:00", 1));

        MutableLiveData<List<ThongBao>> liveDataResult = new MutableLiveData<>();
        liveDataResult.setValue(danhSachGiaDinh);

        when(mockRepository.getDanhSachThongBaoLocal(idNguoiDung)).thenReturn(liveDataResult);

        useCase.execute(idNguoiDung).observeForever(result -> {
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(2, result.size());
            assertEquals("Thông báo học tập", result.get(0).getTieuDe());
            assertEquals(0, result.get(0).getDaXem());

            System.out.println("Unit Test PASSED: Luồng đồng bộ và lấy danh sách thông báo chính xác.");
        });

        verify(mockRepository).dongBoThongBao(idNguoiDung);
        verify(mockRepository).getDanhSachThongBaoLocal(idNguoiDung);
    }
}