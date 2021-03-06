package com.example.restapi.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restapi.common.RestDocsConfiguration;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class IndexControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void index() throws Exception {
		this.mockMvc.perform(get("/api/"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("_links.events").exists());
	}
}
