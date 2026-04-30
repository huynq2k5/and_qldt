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

import vn.huy.quanlydaotao.data.remote.dto.KetQuaRequest;
import vn.huy.quanlydaotao.domain.model.KetQua;
import vn.huy.quanlydaotao.domain.repository.IRepositoryKetQua;

public class NopBaiUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private IRepositoryKetQua mockRepository;
    private NopBaiUseCase useCase;

    @Before
    public void setup() {
        mockRepository = mock(IRepositoryKetQua.class);
        useCase = new NopBaiUseCase(mockRepository);
    }

    @Test
    public void testNopBai_ReturnsCorrectIdAndStatus() {
        List<KetQuaRequest.BaiLam> danhSachBaiLam = new ArrayList<>();
        danhSachBaiLam.add(new KetQuaRequest.BaiLam(10, "A"));
        danhSachBaiLam.add(new KetQuaRequest.BaiLam(11, "C"));

        KetQuaRequest request = new KetQuaRequest(1, 5, danhSachBaiLam);

        KetQua expectedResult = new KetQua("success", 2026);
        MutableLiveData<KetQua> liveDataResult = new MutableLiveData<>();
        liveDataResult.setValue(expectedResult);

        when(mockRepository.nopBai(request)).thenReturn(liveDataResult);

        useCase.execute(request).observeForever(result -> {
            assertNotNull(result);
            assertEquals("success", result.getStatus());
            assertEquals(2026, result.getIdKetQua());

            System.out.println("POST Flow Test PASSED: Luồng nộp bài và nhận ID kết quả chính xác.");
        });

        verify(mockRepository).nopBai(request);
    }
}