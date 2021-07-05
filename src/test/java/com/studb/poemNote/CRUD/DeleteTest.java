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

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DeleteTest {
    
    @Autowired
    private MockMvc mockMvc;

    MockMvcRequestBuilders mrb;
    MockMvcResultHandlers mrh;
    MockMvcResultMatchers mrm;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ObjectMapper om = new ObjectMapper();

    @Test
    void poemDeleteTest() throws Exception {
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
            .perform(mrb.delete("/api/v1/poem/textId"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data.result.Action", is("Erase")))
            .andExpect(mrm.jsonPath("$.data.result.Category", is("Poem")))
            .andExpect(mrm.jsonPath("$.data.result.Error", is(false)))
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void ReviewDeleteTest() throws Exception {
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
            .perform(mrb.delete("/api/v1/review/textId"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data.result.Action", is("Erase")))
            .andExpect(mrm.jsonPath("$.data.result.Category", is("Review")))
            .andExpect(mrm.jsonPath("$.data.result.Error", is(false)))
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void TheoryDeleteTest() throws Exception {
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
            .perform(mrb.delete("/api/v1/theory/textId"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data.result.Action", is("Erase")))
            .andExpect(mrm.jsonPath("$.data.result.Category", is("Theory")))
            .andExpect(mrm.jsonPath("$.data.result.Error", is(false)))
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

    @Test
    void OpinionDeleteTest() throws Exception {
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
            .perform(mrb.delete("/api/v1/opinion/textId"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data.result.Action", is("Erase")))
            .andExpect(mrm.jsonPath("$.data.result.Category", is("Opinion")))
            .andExpect(mrm.jsonPath("$.data.result.Error", is(false)))
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
            .perform(mrb.delete("/api/v1/line/textId"))
            .andExpect(mrm.status().isOk())
            .andExpect(mrm.jsonPath("$.data.result.Action", is("Erase")))
            .andExpect(mrm.jsonPath("$.data.result.Category", is("Line")))
            .andExpect(mrm.jsonPath("$.data.result.Error", is(false)))
            .andExpect(mrm.jsonPath("$.timestamp").exists())
            .andExpect(mrm.jsonPath("$.error").isEmpty())
            .andReturn();
    }

}
