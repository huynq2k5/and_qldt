package vn.huy.quanlydaotao.domain.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        // Repository cần Dao để lấy dữ liệu Local, API truyền null vì ta chỉ test Mapping Local
        BaiKiemTraRepositoryImpl repository = new BaiKiemTraRepositoryImpl(mockDao, null);
        useCase = new LayBaiKiemTraUseCase(repository);
    }

    @Test
    public void testMapping_FromEntityToDomainModel_WithIdKetQua() {
        // Giả lập dữ liệu có ID Kết quả (Sinh viên đã làm bài)
        List<BaiKiemTraEntity> entities = new ArrayList<>();
        entities.add(new BaiKiemTraEntity(1, 10, "Thực hành Android", 5, 90, "Lập trình Di động", 101));

        MutableLiveData<List<BaiKiemTraEntity>> liveDataEntities = new MutableLiveData<>();
        liveDataEntities.setValue(entities);

        when(mockDao.getAll()).thenReturn(liveDataEntities);

        useCase.execute().observeForever(domainModels -> {
            assertNotNull(domainModels);
            assertEquals(1, domainModels.size());

            BaiKiemTra result = domainModels.get(0);
            assertEquals(1, result.getId());
            assertEquals(Integer.valueOf(101), result.getIdKetQua()); // Kiểm tra mapping ID kết quả
            assertEquals("Thực hành Android", result.getTieuDe());

            System.out.println("Test PASSED: Mapping idKetQua (Integer) thành công.");
        });
    }

    @Test
    public void testMapping_FromEntityToDomainModel_WithNullIdKetQua() {
        // Giả lập dữ liệu chưa có ID Kết quả (Sinh viên chưa làm bài)
        List<BaiKiemTraEntity> entities = new ArrayList<>();
        entities.add(new BaiKiemTraEntity(2, 10, "Lý thuyết Kotlin", 5, 45, "Lập trình Di động", null));

        MutableLiveData<List<BaiKiemTraEntity>> liveDataEntities = new MutableLiveData<>();
        liveDataEntities.setValue(entities);

        when(mockDao.getAll()).thenReturn(liveDataEntities);

        useCase.execute().observeForever(domainModels -> {
            assertNotNull(domainModels);
            BaiKiemTra result = domainModels.get(0);

            assertEquals(2, result.getId());
            assertNull(result.getIdKetQua()); // Phải là null nếu chưa làm bài

            System.out.println("Test PASSED: Mapping idKetQua (Null) thành công.");
        });
    }
}