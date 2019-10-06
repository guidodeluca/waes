package com.example.waes.test.controllers;

import com.example.waes.test.api.JsonFile;
import com.example.waes.test.api.Response;
import com.example.waes.test.api.ResponseType;
import com.example.waes.test.repository.ComparisonRepository;
import com.example.waes.test.services.ComparisonService;
import com.example.waes.test.utils.KeyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ComparisonControllerTest {
    private final static String URL_CONTEXT = "/v1/diff";
    private static final String COMPARISON_DATA_1 = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gRG9uZWMgcmhvbmN1cyBzY2VsZXJpc3F1ZSBxdWFtLCBuZWMgbG9ib3J0aXMgcmlzdXMuCg==";
    private static final String COMPARISON_DATA_2 = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gTWFlY2VuYXMgaW4gbG9yZW0gYWNjdW1zYW4sIGVsZWlmZW5kIHNlbSB1dCBudWxsYW0uCg==";
    private static final String COMPARISON_DATA_3 = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gTnVuYyBzb2RhbGVzIGV1aXNtb2QgcmhvbmN1cy4gRHVpcyBzY2VsZXJpc3F1ZSBibGFuZGl0Lgo";

    private JsonFile jsonFileData1, jsonFileData2, jsonFileData3;
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComparisonService comparisonService;
    @Autowired
    private ComparisonRepository comparisonRepository;

    @Before
    public void setUp() {
        jsonFileData1 = new JsonFile("file/plain", COMPARISON_DATA_1);
        jsonFileData2 = new JsonFile("file/plain", COMPARISON_DATA_2);
        jsonFileData3 = new JsonFile("file/plain", COMPARISON_DATA_3);

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddLeftData() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(put(URL_CONTEXT + "/{id}/left", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData1))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddRightData() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(put(URL_CONTEXT + "/{id}/right", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData2))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testCompareEqualsData() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(put(URL_CONTEXT + "/{id}/left", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData1))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        mockMvc.perform(put(URL_CONTEXT + "/{id}/right", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData1))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        ResultActions resultActions = mockMvc.perform(post(URL_CONTEXT + "/{id}", id))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(contentAsString, Response.class);

        assertTrue(response.getType().equals(ResponseType.EQUALS));
    }

    @Test
    public void testCompareEqualsLenghtDifferentData() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(put(URL_CONTEXT + "/{id}/left", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData1))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        mockMvc.perform(put(URL_CONTEXT + "/{id}/right", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData2))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        ResultActions resultActions = mockMvc.perform(post(URL_CONTEXT + "/{id}", id))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(contentAsString, Response.class);

        assertTrue(response.getType().equals(ResponseType.EQUALS));
        assertTrue(response.getDifferences().size() > 0);
    }

    @Test
    public void testCompareDifferentSizeData() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(put(URL_CONTEXT + "/{id}/left", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData1))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        mockMvc.perform(put(URL_CONTEXT + "/{id}/right", id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(jsonFileData3))
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        ResultActions resultActions = mockMvc.perform(post(URL_CONTEXT + "/{id}", id))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Response response = objectMapper.readValue(contentAsString, Response.class);

        assertTrue(response.getType().equals(ResponseType.NOT_EQUALS));
    }

    @Test
    public void testExceptionHandler() throws Exception {
        String id = KeyUtils.createKey();

        mockMvc.perform(post(URL_CONTEXT + "/{id}", id))
                .andExpect(status().isBadRequest());
    }
}
