package cn.edu.zafu.netcontact.model;

public class Model {
	private Integer id;

	public Model() {

	}

	public Model(Integer id, String realName, Integer parent) {
		this.id = id;
		RealName = realName;
		this.parent = parent;
	}

	private String RealName;
	private Integer parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

}
