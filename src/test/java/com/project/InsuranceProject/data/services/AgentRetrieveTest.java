package com.project.InsuranceProject.data.services;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AgentRetrieveTest {

    @Mock
    private UserRepository userRepository;

    private AgentRetrieveService agentRetrieveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agentRetrieveService = new AgentRetrieveService(userRepository);
    }

    @Test
    void testGetAllAgentNames() {
        Users agent1 = new Users();
        agent1.setUsername("agent1test");
        agent1.setRole("ROLE_AGENT");

        Users agent2 = new Users();
        agent2.setUsername("agent2test");
        agent2.setRole("ROLE_AGENT");

        List<Users> agents = Arrays.asList(agent1, agent2);
        when(userRepository.findByRole("ROLE_AGENT")).thenReturn(agents);
        List<String> result = agentRetrieveService.getAllAgentNames();
        assertEquals(Arrays.asList("agent1test", "agent2test"), result);
    }
}