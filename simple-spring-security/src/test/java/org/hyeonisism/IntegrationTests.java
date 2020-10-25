package org.hyeonisism;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author hyeonisism
 */
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTests {

    @Autowired
    protected MockMvc mvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

}
