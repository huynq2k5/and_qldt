package vn.huy.quanlydaotao.ui.quiz;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.huy.quanlydaotao.data.local.dao.BaiLamTamDao;
import vn.huy.quanlydaotao.data.local.entity.BaiLamTamEntity;
import vn.huy.quanlydaotao.domain.model.CauHoi;
import vn.huy.quanlydaotao.domain.usecase.LayCauHoiUseCase;

public class QuizViewModel extends ViewModel {

    private final LayCauHoiUseCase layCauHoiUseCase;
    private final BaiLamTamDao baiLamTamDao;

    private final MutableLiveData<Integer> currentIndex = new MutableLiveData<>(0);
    private final MutableLiveData<String> remainingTime = new MutableLiveData<>();
    private final MutableLiveData<Map<Integer, String>> userAnswers = new MutableLiveData<>(new HashMap<>());

    private List<CauHoi> questionList = new ArrayList<>();
    private CountDownTimer timer;

    public QuizViewModel(LayCauHoiUseCase layCauHoiUseCase, BaiLamTamDao baiLamTamDao) {
        this.layCauHoiUseCase = layCauHoiUseCase;
        this.baiLamTamDao = baiLamTamDao;
    }

    public void loadQuestions(int idUser, int idBkt) {
        layCauHoiUseCase.refresh(idUser, idBkt);
    }

    public LiveData<List<CauHoi>> getQuestions(int idBkt) {
        return layCauHoiUseCase.execute(idBkt);
    }

    public List<CauHoi> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<CauHoi> list) {
        this.questionList = list;
    }

    public LiveData<Integer> getCurrentIndex() { return currentIndex; }
    public LiveData<String> getRemainingTime() { return remainingTime; }
    public LiveData<Map<Integer, String>> getUserAnswers() { return userAnswers; }

    public void nextQuestion() {
        if (currentIndex.getValue() != null && currentIndex.getValue() < questionList.size() - 1) {
            currentIndex.setValue(currentIndex.getValue() + 1);
        }
    }

    public void prevQuestion() {
        if (currentIndex.getValue() != null && currentIndex.getValue() > 0) {
            currentIndex.setValue(currentIndex.getValue() - 1);
        }
    }

    public void setCurrentIndex(int index) {
        currentIndex.setValue(index);
    }

    public void saveAnswer(int questionId, String answer) {
        Map<Integer, String> currentMap = userAnswers.getValue();
        if (currentMap != null) {
            // 1. Cập nhật vào bản đồ tạm trong RAM trước
            currentMap.put(questionId, answer);
            userAnswers.setValue(currentMap); // Kích hoạt Observer để UI biết có thay đổi

            // 2. Lưu vào Database ở background để chống mất dữ liệu
            new Thread(() -> {
                baiLamTamDao.luuCauTraLoi(new BaiLamTamEntity(questionId, answer));
            }).start();
        }
    }

    public void loadSavedAnswers() {
        new Thread(() -> {
            List<BaiLamTamEntity> saved = baiLamTamDao.layToanBoBaiLam();
            Map<Integer, String> currentMap = new HashMap<>();
            for (BaiLamTamEntity entity : saved) {
                currentMap.put(entity.getIdCauHoi(), entity.getCauTraLoi());
            }
            userAnswers.postValue(currentMap);
        }).start();
    }

    public void startTimer(int minutes) {
        if (timer != null) timer.cancel();

        timer = new CountDownTimer(minutes * 60 * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int h = (int) (millisUntilFinished / 3600000);
                int m = (int) (millisUntilFinished % 3600000) / 60000;
                int s = (int) (millisUntilFinished % 60000) / 1000;
                remainingTime.setValue(String.format(Locale.getDefault(), "%02d : %02d : %02d", h, m, s));
            }

            @Override
            public void onFinish() {
                remainingTime.setValue("00 : 00 : 00");
            }
        }.start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (timer != null) timer.cancel();
    }
}