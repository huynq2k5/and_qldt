package vn.huy.quanlydaotao;

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

import vn.huy.quanlydaotao.data.remote.dto.TienDoResponse;
import vn.huy.quanlydaotao.domain.repository.ITienDoRepository;
import vn.huy.quanlydaotao.domain.usecase.CapNhatTienDoUseCase;

public class CapNhatTienDoUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ITienDoRepository mockRepository;
    private CapNhatTienDoUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(ITienDoRepository.class);
        useCase = new CapNhatTienDoUseCase(mockRepository);
    }

    @Test
    public void testCapNhatTienDo_ReturnsSuccessAndMessage() {
        int idNguoiDung = 23004224;
        int idBaiHoc = 42;
        int phanTram = 100;

        TienDoResponse expectedResponse = mock(TienDoResponse.class);
        when(expectedResponse.isSuccess()).thenReturn(true);
        when(expectedResponse.getMessage()).thenReturn("Cập nhật tiến độ thành công");

        MutableLiveData<TienDoResponse> liveDataResult = new MutableLiveData<>();
        liveDataResult.setValue(expectedResponse);

        when(mockRepository.luuTienDoRemote(idNguoiDung, idBaiHoc, phanTram)).thenReturn(liveDataResult);

        useCase.thucHienCapNhat(idNguoiDung, idBaiHoc, phanTram).observeForever(result -> {
            assertNotNull(result);
            assertTrue(result.isSuccess());
            assertEquals("Cập nhật tiến độ thành công", result.getMessage());

            System.out.println("Unit Test PASSED: Luồng cập nhật tiến độ hoạt động chính xác.");
        });

        verify(mockRepository).luuTienDoRemote(idNguoiDung, idBaiHoc, phanTram);
    }
}