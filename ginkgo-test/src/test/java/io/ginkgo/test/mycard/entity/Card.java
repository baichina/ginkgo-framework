package io.ginkgo.test.mycard.entity;

public class Card {
	private Integer id;
	private String name;
	private Integer tl;
	private Integer fl;
	private Integer gj;
	private Integer fy;
	private Integer jn;

	public Card(Integer id, String name, Integer tl, Integer fl, Integer gj, Integer fy, Integer jn) {
		super();
		this.id = id;
		this.name = name;
		this.tl = tl;
		this.fl = fl;
		this.gj = gj;
		this.fy = fy;
		this.jn = jn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTl() {
		return tl;
	}

	public void setTl(Integer tl) {
		this.tl = tl;
	}

	public Integer getFl() {
		return fl;
	}

	public void setFl(Integer fl) {
		this.fl = fl;
	}

	public Integer getGj() {
		return gj;
	}

	public void setGj(Integer gj) {
		this.gj = gj;
	}

	public Integer getFy() {
		return fy;
	}

	public void setFy(Integer fy) {
		this.fy = fy;
	}

	public Integer getJn() {
		return jn;
	}

	public void setJn(Integer jn) {
		this.jn = jn;
	}
}