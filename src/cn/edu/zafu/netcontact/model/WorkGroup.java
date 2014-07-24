package cn.edu.zafu.netcontact.model;

import java.util.List;

public class WorkGroup {
	public String RealName;
	private List<Person> Persons;
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}
	public List<Person> getPersons() {
		return Persons;
	}
	public void setPersons(List<Person> data) {
		Persons = data;
	}
	@Override
	public String toString() {
		return "WorkGroup:"+RealName;
	}
}
