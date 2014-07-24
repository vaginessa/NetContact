package cn.edu.zafu.netcontact.model;

import java.util.List;

public class Department {
	public String RealName;
	private List<WorkGroup> WorkGroups;
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public List<WorkGroup> getWorkGroups() {
		return WorkGroups;
	}
	public void setWorkGroups(List<WorkGroup> workGroups) {
		WorkGroups = workGroups;
	}
	@Override
	public String toString() {
		return "Department:"+RealName;
	}
}
