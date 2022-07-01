package com.api.codingchallenge.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_comments")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String imdbId;

	@Column(nullable = false)
	private String comment;

	@Column(nullable = false)
	private Boolean repeated = false;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment commentAnswered;

	@OneToMany(mappedBy = "commentAnswered", cascade = CascadeType.ALL)
	private List<Comment> commentAnswers = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	private List<CommentReaction> commentReactions = new ArrayList<>();

	@Column(nullable = false)
	private Instant createdAt = Instant.now();

	public Comment() {
	}

	public Comment(String imdbId, String comment, Boolean repeated, Comment commentAnswered, User user) {
		this.imdbId = imdbId;
		this.comment = comment;
		this.repeated = repeated;
		this.commentAnswered = commentAnswered;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getRepeated() {
		return repeated;
	}

	public void setRepeated(Boolean repeated) {
		this.repeated = repeated;
	}

	public Comment getCommentAnswered() {
		return commentAnswered;
	}

	public void setCommentAnswered(Comment commentAnswered) {
		this.commentAnswered = commentAnswered;
	}

	public List<Comment> getCommentAnswers() {
		return commentAnswers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CommentReaction> getCommentReactions() {
		return commentReactions;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
