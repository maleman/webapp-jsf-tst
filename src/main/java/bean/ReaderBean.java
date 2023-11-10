package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

@SuppressWarnings("rawtypes")
@ManagedBean(name = "readerBean")
public class ReaderBean {
	private dao.ReaderxDao readerDao;
	
	private List readers;

	private String editId;
	private String name;

	@PostConstruct
	private void init() {
		readerDao = new dao.ReaderxDao();
	}

	public List getReaders() {
		readers = readerDao.list();
		return readers;
	}

	public String deleteByID(int id) {
		readerDao.deleteReader(id);
		return "readers.xhtml?faces-redirect=true";
	}

	public String editByID() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectedreaderId");
		var r = readerDao.getReaderByID(Integer.valueOf(id));
		this.setEditId(Integer.valueOf(id).toString());
		this.setName(r.getName());
		PrimeFaces.current().ajax().update(":readerEditForm");
		return "";
	}

	public String saveEdit() {
		try {
			String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("editedId");
			int id = Integer.valueOf(ids);
			readerDao.updateReader(id, this.name);
			return "readers.xhtml?faces-redirect=true";
		}catch(Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
		}
		return "";
	}

	public String addNew() {
		try {
			if (name != null && name.length() > 0){
				readerDao.createNewReader(name);
				return "readers.xhtml?faces-redirect=true";
			}
		}catch(Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReaders(List readers) {
		this.readers = readers;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

}
