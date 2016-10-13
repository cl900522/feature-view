package acme.me.orm.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class JoinKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -790026586207522393L;

	@Column(name = "firstKey")
	private String firstKey;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(columnDefinition = "AreaCode", referencedColumnName = "code")
	private AreaNode area;

	public String getFirstKey() {
		return firstKey;
	}

	public void setFirstKey(String firstKey) {
		this.firstKey = firstKey;
	}

	public AreaNode getArea() {
		return area;
	}

	public void setArea(AreaNode area) {
		this.area = area;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((firstKey == null) ? 0 : firstKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JoinKey other = (JoinKey) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (firstKey == null) {
			if (other.firstKey != null)
				return false;
		} else if (!firstKey.equals(other.firstKey))
			return false;
		return true;
	}

}
