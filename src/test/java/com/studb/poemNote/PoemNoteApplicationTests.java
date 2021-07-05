package com.studb.poemNote;

import com.studb.poemNote.repository.backup.BackupRepository;
import com.studb.poemNote.repository.category.CategoryRepository;
import com.studb.poemNote.repository.line.LineRepository;
import com.studb.poemNote.repository.log.LogRepository;
import com.studb.poemNote.repository.opinion.OpinionRepository;
import com.studb.poemNote.repository.poem.PoemRepository;
import com.studb.poemNote.repository.review.ReviewRepository;
import com.studb.poemNote.repository.theory.TheoryRepository;
import com.studb.poemNote.repository.user.UserRepository;
import com.studb.poemNote.service.BackupService;
import com.studb.poemNote.service.CategoryService;
import com.studb.poemNote.service.LineService;
import com.studb.poemNote.service.LogService;
import com.studb.poemNote.service.OpinionService;
import com.studb.poemNote.service.PoemService;
import com.studb.poemNote.service.ReviewService;
import com.studb.poemNote.service.TheoryService;
import com.studb.poemNote.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PoemNoteApplicationTests {

	ApplicationContext ac = new AnnotationConfigApplicationContext(PoemNoteApplication.class);

	// Reposiroy
	BackupRepository br = ac.getBean("backupRepository", BackupRepository.class);
	CategoryRepository cr = ac.getBean("categoryRepository", CategoryRepository.class);
	LineRepository lr = ac.getBean("lineRepository", LineRepository.class);
	LogRepository logr = ac.getBean("logRepository", LogRepository.class);
	OpinionRepository or = ac.getBean("opinionRepository", OpinionRepository.class);
	PoemRepository pr = ac.getBean("poemRepository", PoemRepository.class);
	ReviewRepository rr = ac.getBean("reviewRepository", ReviewRepository.class);
	TheoryRepository tr = ac.getBean("theoryRepository", TheoryRepository.class);
	UserRepository ur = ac.getBean("userRepository", UserRepository.class);

	// Service
	BackupService bs = ac.getBean("backupService", BackupService.class);
	CategoryService cs = ac.getBean("categoryService", CategoryService.class);
	LineService ls = ac.getBean("lineService", LineService.class);
	LogService logs = ac.getBean("logService", LogService.class);
	OpinionService os = ac.getBean("opinionService", OpinionService.class);
	PoemService ps = ac.getBean("poemService", PoemService.class);
	ReviewService rs = ac.getBean("reviewService", ReviewService.class);
	TheoryService ts = ac.getBean("theoryService", TheoryService.class);
	UserService us = ac.getBean("userService", UserService.class);

	@Autowired
	private MockMvc mockMvc;

	MockMvcRequestBuilders mrb;
    MockMvcResultHandlers mrh;
    MockMvcResultMatchers mrm;

	@Test
	void operationTest() {
		br.findAll();
		cr.findAll();
		lr.findAll();
		logr.findAll();
		or.findAll();
		pr.findAll();
		rr.findAll();
		tr.findAll();
		ur.findAll();
		//
		bs.findAll();
		cs.findAll();
		ls.findAll();
		logs.findAll();
		os.findAll();
		ps.findAll();
		rs.findAll();
		ts.findAll();
		us.findAll();
	}

	@Test
	void apiTest() throws Exception {
		mockMvc
			.perform(mrb.get("/api/v1/backup"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/category"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/line"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/log"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/opinion"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/poem"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/review"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/theory"))
			.andExpect(mrm.status().isOk());
		mockMvc
			.perform(mrb.get("/api/v1/user"))
			.andExpect(mrm.status().isOk());
	}

}
