package com.javaex.vo;

public class BoardVo {

	    
	// 필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String id;
	private String name;
	
	// 생성자	
	public BoardVo() {
	}

	/*******modify 생성자*****/
	public BoardVo(int no, String title, String content) {
		this.no = no;
		this.title = title;
		this.content = content;
	}

	/*****write 생성자*****/
	public BoardVo(String title, String content, int userNo) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}
	
	
	


	/**********read test*********/
	public BoardVo(String title, String content, int hit, String regDate, String id, String name) {
		super();
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.id = id;
		this.name = name;
	}

	
	
	
	public BoardVo(String title, String content, int hit, String regDate, int userNo, String id, String name) {
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.id = id;
		this.name = name;
	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String name) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.name = name;
	}

	
	
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String id, String name) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.id = id;
		this.name = name;
	}
	
	
	
	// 메소드 g/s


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getHit() {
		return hit;
	}


	public void setHit(int hit) {
		this.hit = hit;
	}


	public String getRegDate() {
		return regDate;
	}


	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	// 메소드 일반


	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", id=" + id + ", name=" + name + "]";
	}
	

	
	
}
