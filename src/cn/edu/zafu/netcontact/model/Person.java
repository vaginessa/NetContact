package cn.edu.zafu.netcontact.model;

public class Person {
	private String PerId;
	private String RealName;
	private String SubCompany;
	private String Company;
	private String Department;
	private String Workgroup;
	private String Tel1;
	private String Tel2;
	private String Tel3;
	private String ModifyDate;

	public String getCompany() {
		return Company;
	}

	public String getDepartment() {
		return Department;
	}

	public String getModifyDate() {
		return ModifyDate;
	}

	public String getPerId() {
		return PerId;
	}

	public String getRealName() {
		return RealName;
	}

	public String getSubCompany() {
		return SubCompany;
	}

	public String getTel1() {
		return Tel1;
	}

	public String getTel2() {
		return Tel2;
	}

	public String getTel3() {
		return Tel3;
	}

	public String getWorkgroup() {
		return Workgroup;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public void setModifyDate(String modifyDate) {
		ModifyDate = modifyDate;
	}

	public void setPerId(String perId) {
		PerId = perId;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public void setSubCompany(String subCompany) {
		SubCompany = subCompany;
	}

	public void setTel1(String tel1) {
		Tel1 = tel1;
	}

	public void setTel2(String tel2) {
		Tel2 = tel2;
	}

	public void setTel3(String tel3) {
		Tel3 = tel3;
	}

	public void setWorkgroup(String workgroup) {
		Workgroup = workgroup;
	}

	public String Tel1() {
		return Department;
	}

	public String toString() {
		return "Person:"+PerId+" "+RealName+" "+SubCompany+" "+Company+" "+Department+" "+Workgroup+" "+Tel1+" "+Tel2+" "+Tel3+" "+ModifyDate;
	}

}
