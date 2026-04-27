package vn.huy.quanlydaotao.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import vn.huy.quanlydaotao.data.local.dao.BaiKiemTraDao;
import vn.huy.quanlydaotao.data.local.entity.BaiKiemTraEntity;
import vn.huy.quanlydaotao.data.repository.BaiKiemTraRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.BaiKiemTra;

public class BaiKiemTraLocalMappingTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BaiKiemTraDao mockDao;
    private LayBaiKiemTraUseCase useCase;

    @Before
    public void setup() {
        mockDao = mock(BaiKiemTraDao.class);

        BaiKiemTraRepositoryImpl repository = new BaiKiemTraRepositoryImpl(mockDao, null);
        useCase = new LayBaiKiemTraUseCase(repository);
    }

    @Test
    public void testMapping_FromEntityToDomainModel() {
        List<BaiKiemTraEntity> entities = new ArrayList<>();
        entities.add(new BaiKiemTraEntity(1, 10, "Thực hành Android", 5, 90, "Lập trình Di động"));

        MutableLiveData<List<BaiKiemTraEntity>> liveDataEntities = new MutableLiveData<>();
        liveDataEntities.setValue(entities);

        when(mockDao.getAll()).thenReturn(liveDataEntities);

        useCase.execute().observeForever(domainModels -> {
            assertNotNull(domainModels);
            assertEquals(1, domainModels.size());

            BaiKiemTra result = domainModels.get(0);
            assertEquals(1, result.getId());
            assertEquals("Thực hành Android", result.getTieuDe());
            assertEquals("Lập trình Di động", result.getTenKhoaHoc());

            System.out.println("Mapping Test PASSED: Entity đã chuyển đổi thành Domain Model chính xác.");
        });
    }
}