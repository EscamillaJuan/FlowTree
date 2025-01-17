package com.escamilla.flow_tree;

import com.escamilla.flow_tree.model.repository.UserRepository;
import com.escamilla.flow_tree.service.AuthService;
import com.escamilla.flow_tree.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FlowTreeApplicationTests {

	@Mock
	private UserRepository userRepository;
	@Mock
	private JwtService jwtService;
	@Mock
	AuthService authService;


	@Test
	void contextLoads() {
	}

}
