package vn.huy.quanlydaotao.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;
import vn.huy.quanlydaotao.domain.repository.INguoiDungRepository;

public class SuaThongTinUserUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private INguoiDungRepository mockRepository;
    private SuaThongTinUserUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(INguoiDungRepository.class);
        useCase = new SuaThongTinUserUseCase(mockRepository);
    }

    @Test
    public void testSuaThongTin_ReturnsSuccessAndMessage() {
        int id = 2;
        String hoTenMoi = "Nguyễn Quang Huy";
        String emailMoi = "huy.new@vlute.edu.vn";

        NguoiDungResponse expectedResponse = mock(NguoiDungResponse.class);
        when(expectedResponse.isSuccess()).thenReturn(true);
        when(expectedResponse.getMessage()).thenReturn("Cập nhật thông tin thành công");

        MutableLiveData<NguoiDungResponse> liveDataResult = new MutableLiveData<>();
        liveDataResult.setValue(expectedResponse);

        when(mockRepository.suaThongTin(id, hoTenMoi, emailMoi)).thenReturn(liveDataResult);

        useCase.thucHien(id, hoTenMoi, emailMoi).observeForever(result -> {
            assertNotNull(result);
            assertTrue(result.isSuccess());
            assertEquals("Cập nhật thông tin thành công", result.getMessage());

            System.out.println("Unit Test PASSED: Luồng sửa thông tin hoạt động chính xác.");
        });

        verify(mockRepository).suaThongTin(id, hoTenMoi, emailMoi);
    }
}