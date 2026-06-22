package com.torloksz.arethium.service;

import com.torloksz.arethium.dto.MessageDTO;
import com.torloksz.arethium.entity.Modules;
import com.torloksz.arethium.entity.Users;
import com.torloksz.arethium.repository.ModulesRepository;
import com.torloksz.arethium.session.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserSession userSession;
    Model model;
    private final ModulesRepository modulesRepository;

    public MessageDTO isUserSessionRunning() {
        Users users = userSession.getUser();
        if(users==null)
            return new MessageDTO("User is not Logged in..");
        return new MessageDTO("Success");
    }

    public Map<String, Object> getStats() {
        if (isUserSessionRunning().message().contains("Success")){

            Users user = userSession.getUser();
            Map<String, Object> stats = new HashMap<>();
            stats.put("targetRole", user.getGoals().getTargetRole());
            stats.put("targetCompany", user.getGoals().getTargetCompany());
            stats.put("userName", user.getName());
            stats.put("readiness", findReadiness(user));
            stats.put("rank", findRank(user));
            stats.put("time", findTime(user));
            stats.put("modules", getModules(user));

            return stats;
        }
        return new HashMap<>();
    }

    public double findReadiness(Users user) {
        List<Modules> modules = modulesRepository.findByUsersId(user.getId());
        long completed = modules.stream()
                .filter(Modules::isCompleted)
                .count();
        return (((double) completed /modules.size())*100);
    }

    public String findRank(Users user) {
        double completed = findReadiness(user);
        if(completed<=20) return "D";
        else if (completed<=50) return "C";
        else if (completed<=65) return "B";
        else if (completed<=80) return "A";
        else return "S";
    }

    public long findTime(Users user) {
        List<Modules> modules = modulesRepository.findByUsersId(user.getId());
        long timeLeft=0;
        for (Modules m:modules){
            if (!m.isCompleted())
                timeLeft+=m.getTime();
        }
        return timeLeft;
    }

    public List<Modules> getModules(Users user) {
        return modulesRepository.findByUsersId(user.getId());
    }

    public List<Modules> getModules() {
        return modulesRepository.findByUsersId(userSession.getUser().getId());
    }

    public void toggleModule(Long id) {
        Modules modules = modulesRepository.findModulesById(id);
        modules.setCompleted(true);
        modulesRepository.save(modules);
    }

}
