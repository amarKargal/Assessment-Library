package com.pratham.assessment_lib.services.stt_service;

import java.util.ArrayList;

public interface STT_Result {

    void Stt_onResult(ArrayList<String> sttResult);
    void Stt_onError();

}
