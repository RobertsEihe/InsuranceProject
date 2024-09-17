package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentRetrieveService {

    private final UserRepository agentRepository;

    public AgentRetrieveService(UserRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<Users> getAgentid() {
        return agentRepository.findByRole("agent");
    }
    public List<String> getAllAgentNames() {
        return agentRepository.findByRole("ROLE_AGENT")
                .stream()
                .map(Users::getUsername) // Assuming Agent entity has a getName() method
                .collect(Collectors.toList());
    }
}
