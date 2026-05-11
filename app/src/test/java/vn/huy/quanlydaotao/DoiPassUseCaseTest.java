package vn.huy.quanlydaotao.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import vn.huy.quanlydaotao.data.remote.dto.DoiPassRequest;
import vn.huy.quanlydaotao.data.remote.dto.DoiPassResponse;
import vn.huy.quanlydaotao.domain.repository.IDoiPassRepository;

public class DoiPassUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private IDoiPassRepository mockRepository;
    private DoiPassUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(IDoiPassRepository.class);
        useCase = new DoiPassUseCase(mockRepository);
    }

    @Test
    public void testDoiMatKhau_ReturnsSuccess() {
        int id = 23004224;
        String passCu = "matkhau123";
        String passMoi = "matkhau2026";

        DoiPassResponse expectedResponse = mock(DoiPassResponse.class);
        when(expectedResponse.isSuccess()).thenReturn(true);
        when(expectedResponse.getMessage()).thenReturn("Đổi mật khẩu thành công");

        MutableLiveData<DoiPassResponse> liveDataResult = new MutableLiveData<>();
        liveDataResult.setValue(expectedResponse);

        when(mockRepository.doiMatKhau(any(DoiPassRequest.class))).thenReturn(liveDataResult);

        useCase.thucHien(id, passCu, passMoi).observeForever(result -> {
            assertNotNull(result);
            assertTrue(result.isSuccess());
            assertEquals("Đổi mật khẩu thành công", result.getMessage());

            System.out.println("Unit Test PASSED: Luồng đổi mật khẩu hoạt động chính xác.");
        });

        verify(mockRepository).doiMatKhau(any(DoiPassRequest.class));
    }
}