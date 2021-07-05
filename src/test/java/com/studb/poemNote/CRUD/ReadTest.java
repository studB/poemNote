package com.studb.poemNote.CRUD;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studb.poemNote.domain.line.Line;
import com.studb.poemNote.domain.opinion.Opinion;
import com.studb.poemNote.domain.poem.Poem;
import com.studb.poemNote.domain.review.Review;
import com.studb.poemNote.domain.theory.Theory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReadTest {
    
    @Autowired
    private MockMvc mockMvc;

    MockMvcRequestBuilders mrb;
    MockMvcResultHandlers mrh;
    MockMvcResultMatchers mrm;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ObjectMapper om = new ObjectMapper();

    @Test
    void poemReadTest() throws Exception {
        Poem data = Poem.builder()
                        .textId("textId")
                        .published(false)
                        .publishIndex(0)
                        .publishTime(timestamp)
                        .title("title")
                        .body("body")
                        .tag("tag")
                        .completedStatus("completedStatus")
                        .writtenStatus("writtenStatus")
                        .valueStatus("valueStatus")
                        .createdAt(timestamp)
                        .modifiedAt(timestamp)
                        .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/poem")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
        
        mockMvc
            .perform(mrb.get("/api/v1/poem"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void poemNoPublishedReadTest() throws Exception {
        Poem data = Poem.builder()
                        .textId("textId")
                        .published(false)
                        .publishIndex(0)
                        .publishTime(timestamp)
                        .title("title")
                        .body("body")
                        .tag("tag")
                        .completedStatus("completedStatus")
                        .writtenStatus("writtenStatus")
                        .valueStatus("valueStatus")
                        .createdAt(timestamp)
                        .modifiedAt(timestamp)
                        .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/poem")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
        
        mockMvc
            .perform(mrb.get("/api/v1/poem/nopublished"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void ReviewReadTest() throws Exception {
        Review data = Review.builder()
                            .textId("textId")
                            .published(false)
                            .publishIndex(0)
                            .publishTime(timestamp)
                            .title("title")
                            .body("body")
                            .tag("tag")
                            .writtenStatus("writtenStatus")
                            .valueStatus("valueStatus")
                            .createdAt(timestamp)
                            .modifiedAt(timestamp)
                            .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/review")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
            
        mockMvc
            .perform(mrb.get("/api/v1/review"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void ReviewNoPublishedReadTest() throws Exception {
        Review data = Review.builder()
                            .textId("textId")
                            .published(false)
                            .publishIndex(0)
                            .publishTime(timestamp)
                            .title("title")
                            .body("body")
                            .tag("tag")
                            .writtenStatus("writtenStatus")
                            .valueStatus("valueStatus")
                            .createdAt(timestamp)
                            .modifiedAt(timestamp)
                            .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/review")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
            
        mockMvc
            .perform(mrb.get("/api/v1/review/nopublished"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void TheoryReadTest() throws Exception {
        Theory data = Theory.builder()
                            .textId("textId")
                            .title("title")
                            .body("body")
                            .tag("tag")
                            .createdAt(timestamp)
                            .modifiedAt(timestamp)
                            .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/theory")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
            
        mockMvc
            .perform(mrb.get("/api/v1/theory"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void OpinionReadTest() throws Exception {
        Opinion data = Opinion.builder()
                            .textId("textId")
                            .body("body")
                            .tag("tag")
                            .createdAt(timestamp)
                            .modifiedAt(timestamp)
                            .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/opinion")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);

        mockMvc
            .perform(mrb.get("/api/v1/opinion"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void LineReadTest() throws Exception {
        Line data = Line.builder()
                        .textId("textId")
                        .body("body")
                        .createdAt(timestamp)
                        .build();
        String json = om.writeValueAsString(data);
        RequestBuilder request = mrb.post("/api/v1/line")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request);
        
        mockMvc
            .perform(mrb.get("/api/v1/line"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data").isArray())
            .andExpect(mrm.jsonPath("$.data[0].textId").exists())
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

}
