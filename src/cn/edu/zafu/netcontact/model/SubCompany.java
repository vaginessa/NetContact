package cn.edu.zafu.netcontact.model;

import java.util.List;

public class SubCompany {
	public String RealName;
	private List<Company> Companys;
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public List<Company> getCompanys() {
		return Companys;
	}
	public void setCompanys(List<Company> Companys) {
		this.Companys = Companys;
	}
	@Override
	public String toString() {
		return "SubCompany:"+RealName;
	}
}
