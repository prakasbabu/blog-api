package com.prakash.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	private String content;
	
	private Date date;
	
	@ManyToOne
	private Post post;
	
	

}
