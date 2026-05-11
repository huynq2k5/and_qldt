package vn.huy.quanlydaotao.ui.canhan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.huy.quanlydaotao.data.local.TokenManager;
import vn.huy.quanlydaotao.data.remote.dto.NguoiDungResponse;
import vn.huy.quanlydaotao.domain.usecase.SuaThongTinUserUseCase;

public class EditProfileViewModel extends ViewModel {
    private final SuaThongTinUserUseCase suaThongTinUseCase;
    private final TokenManager tokenManager;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public EditProfileViewModel(SuaThongTinUserUseCase useCase, TokenManager tokenManager) {
        this.suaThongTinUseCase = useCase;
        this.tokenManager = tokenManager;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public String getCurrentName() {
        return tokenManager.layHoTen();
    }

    public String getCurrentEmail() {
        return tokenManager.layEmail();
    }

    public LiveData<NguoiDungResponse> updateProfile(String newName, String newEmail) {
        loading.setValue(true);
        int userId = tokenManager.layId();

        // Trả về LiveData từ UseCase để Activity quan sát
        return suaThongTinUseCase.thucHien(userId, newName, newEmail);
    }

    public void saveLocal(String name, String email) {
        tokenManager.capNhatHoTenEmail(name, email);
    }
}