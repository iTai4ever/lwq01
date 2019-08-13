package cn.techaction.vo;
//可以实现一个功能对两个表的实现操作
public class ActionUserVo {
		private Integer id;
		private String account;
		private String name;
		private String sex;//修改性别输入问题，默认男为0，女为1
		private Integer age;
		private String phone;
		private String email;
		public Integer getRole() {
			return role;
		}
		public void setRole(Integer role) {
			this.role = role;
		}
		private Integer role;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public 	String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		
}
