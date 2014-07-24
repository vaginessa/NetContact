package cn.edu.zafu.netcontact.model;

import java.util.List;

public class Root {
	private int totalNum;
	private List<SubCompany> SubCompanys;
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public List<SubCompany> getSubCompanys() {
		return SubCompanys;
	}
	public void setSubCompanys(List<SubCompany> subCompanys) {
		SubCompanys = subCompanys;
	}

}
