package bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.LoginDao;
import utils.SessionUtils;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	
	private LoginDao loginDao;
	
	private String userName;
	private String password;

	@PostConstruct
	private void init() {
		this.loginDao = new LoginDao();
	}
	
	//validate login
		public String validateUsernamePassword() {
			boolean valid = loginDao.validate(userName, password);
			if (valid) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", userName);
				return "/views/index.xhtml";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login.xhtml";
			}
		}

		//logout event, invalidate session
		public String logout() {
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			return "/blog/login.xhtml";
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
}
