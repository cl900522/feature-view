package acme.me.orm.bean;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "JoinObject")
//@IdClass(JoinKey.class)
public class JoinObject implements Serializable{
	@EmbeddedId
	private JoinKey id;
	
	public JoinKey getId() {
		return id;
	}

	public void setId(JoinKey id) {
		this.id = id;
	}

//	@Id
//	private String firstKey;
//	@Id
//	private AreaNode area;
//
//	public String getFirstKey() {
//		return firstKey;
//	}
//
//	public void setFirstKey(String firstKey) {
//		this.firstKey = firstKey;
//	}
//
//	public AreaNode getArea() {
//		return area;
//	}
//
//	public void setArea(AreaNode area) {
//		this.area = area;
//	}

}
