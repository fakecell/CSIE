package com.smartcycling.backend.service.user.account;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> register(String username, String passoword, String confirmedPassword);
}
