package com.torloksz.arethium.service;

import com.torloksz.arethium.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserSession userSession;
}
