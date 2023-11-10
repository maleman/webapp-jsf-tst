package dao;

public class LoginDao {
	
	public boolean validate(String userName, String pasword) {
		return (userName != null && pasword != null) 
				&&(userName.equals("admin") && pasword.equals("123"));
	}
}
