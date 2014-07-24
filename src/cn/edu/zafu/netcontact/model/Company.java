package cn.edu.zafu.netcontact.model;

import java.util.List;

public class Company {
	public String RealName;
	private List<Department> Departments;
	
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public List<Department> getDepartments() {
		return Departments;
	}
	public void setDepartments(List<Department> Departments) {
		this.Departments = Departments;
	}
	@Override
	public String toString() {
		return "Company:"+RealName;
	}
}
