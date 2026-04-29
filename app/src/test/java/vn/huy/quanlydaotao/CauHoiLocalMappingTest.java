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

import vn.huy.quanlydaotao.data.local.dao.CauHoiDao;
import vn.huy.quanlydaotao.data.local.entity.CauHoiEntity;
import vn.huy.quanlydaotao.data.repository.CauHoiRepositoryImpl;
import vn.huy.quanlydaotao.domain.model.CauHoi;

public class CauHoiLocalMappingTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CauHoiDao mockDao;
    private LayCauHoiUseCase useCase;

    @Before
    public void setup() {
        mockDao = mock(CauHoiDao.class);
        CauHoiRepositoryImpl repository = new CauHoiRepositoryImpl(mockDao, null);
        useCase = new LayCauHoiUseCase(repository);
    }

    @Test
    public void testMapping_FromEntityToDomainModel() {
        int idBaiKiemTra = 100;
        List<CauHoiEntity> entities = new ArrayList<>();
        entities.add(new CauHoiEntity(
                1,
                idBaiKiemTra,
                "Lớp nào trong Android dùng để lưu trữ dữ liệu cục bộ?",
                "Activity",
                "Service",
                "Room Database",
                "Intent"
        ));

        MutableLiveData<List<CauHoiEntity>> liveDataEntities = new MutableLiveData<>();
        liveDataEntities.setValue(entities);

        when(mockDao.getCauHoiByBaiKiemTra(idBaiKiemTra)).thenReturn(liveDataEntities);

        useCase.execute(idBaiKiemTra).observeForever(domainModels -> {
            assertNotNull(domainModels);
            assertEquals(1, domainModels.size());

            CauHoi result = domainModels.get(0);
            assertEquals(1, result.getId());
            assertEquals(idBaiKiemTra, result.getIdBaiKiemTra());
            assertEquals("Lớp nào trong Android dùng để lưu trữ dữ liệu cục bộ?", result.getNoiDung());
            assertEquals("Room Database", result.getCauC());

            System.out.println("Mapping Test PASSED: Dữ liệu câu hỏi chuyển đổi chính xác.");
        });
    }
}