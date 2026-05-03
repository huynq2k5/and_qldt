package vn.huy.quanlydaotao.domain.usecase;

import vn.huy.quanlydaotao.domain.repository.IFCMRepository;

public class UpdateFcmTokenUseCase {
    private final IFCMRepository repository;

    public UpdateFcmTokenUseCase(IFCMRepository repository) {
        this.repository = repository;
    }

    public void execute(int idUser, String token) {
        if (idUser > 0 && token != null && !token.isEmpty()) {
            repository.updateFcmToken(idUser, token);
        }
    }
}