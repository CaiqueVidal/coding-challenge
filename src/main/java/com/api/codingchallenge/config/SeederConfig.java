package com.api.codingchallenge.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.codingchallenge.model.Comment;
import com.api.codingchallenge.model.CommentReaction;
import com.api.codingchallenge.model.Evaluation;
import com.api.codingchallenge.model.User;
import com.api.codingchallenge.repository.CommentReactionRepository;
import com.api.codingchallenge.repository.CommentRepository;
import com.api.codingchallenge.repository.EvaluationRepository;
import com.api.codingchallenge.repository.UserRepository;

@Configuration
public class SeederConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CommentReactionRepository commentReactionRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User("Alex", "611.998.780-06", "alex@email.com", encoder.encode("alex1234"), 1, 18);
		User u2 = new User("Carla", "907.529.930-38", "carla@email.com", encoder.encode("carla1234"), 2, 18);
		User u3 = new User("Pedro", "961.901.930-05", "pedro@email.com", encoder.encode("pedro1234"), 3, 998);
		User u4 = new User("Larissa", "708.412.660-00", "larissa@email.com", encoder.encode("larissa1234"), 4, 1500);

		Evaluation e1 = new Evaluation(6.9f, "tt4574334", u1);
		Evaluation e2 = new Evaluation(8.4f, "tt4574334", u2);
		Evaluation e3 = new Evaluation(9.2f, "tt4574334", u3);
		Evaluation e4 = new Evaluation(7.6f, "tt2442560", u4);
		Evaluation e5 = new Evaluation(9.4f, "tt2442560", u1);

		Comment c1 = new Comment("tt4574334", "Não achei tão boa assim :(", false, null, u1);
		Comment c2 = new Comment("tt4574334", "Que série boa!", false, null, u2);
		Comment c3 = new Comment("tt4574334", "Recomendo", false, null, u3);
		Comment c4 = new Comment("tt4574334", "Não achei tão boa assim2 :(", true, null, u4);
		Comment c5 = new Comment("tt4574334", "Lógico que é bom", true, c1, u1);
		Comment c6 = new Comment("tt2442560", "Muito Legal", false, null, u4);
		Comment c7 = new Comment("tt2442560", "Esse sim é bom", false, null, u1);

		CommentReaction cr1 = new CommentReaction(c1, u4, 2);
		CommentReaction cr2 = new CommentReaction(c1, u2, 2);
		CommentReaction cr3 = new CommentReaction(c6, u1, 1);
		CommentReaction cr4 = new CommentReaction(c6, u3, 2);

		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));
		evaluationRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5));
		commentRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7));
		commentReactionRepository.saveAll(Arrays.asList(cr1, cr2, cr3, cr4));
	}

}
