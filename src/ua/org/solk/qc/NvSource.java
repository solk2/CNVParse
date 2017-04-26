package ua.org.solk.qc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NvSource implements Serializable {
	private static final long serialVersionUID = -2001694136162597946L;
	
	@XmlElement(name="NvItem")
	private List<NvItem> nvItems;
	
	public List<NvItem> getNvItems() {
		if (nvItems == null)
			nvItems = new ArrayList<>();
		return nvItems;
	}
	
	@XmlType
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class NvItem implements Serializable {
		private static final long serialVersionUID = 9166663231650662836L;
		
		@XmlAttribute
		private int id;
		@XmlAttribute
		private Integer subscriptionid;
		@XmlAttribute
		private String name;
		@XmlAttribute
		private String mapping;
		@XmlAttribute
		private String encoding;
		@XmlAttribute
		private Integer index;
		
		@XmlValue
		private String data;
		
		@XmlTransient
		private byte[] byteData;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Integer getSubscriptionid() {
			return subscriptionid;
		}

		public void setSubscriptionid(Integer subscriptionid) {
			this.subscriptionid = subscriptionid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMapping() {
			return mapping;
		}

		public void setMapping(String mapping) {
			this.mapping = mapping;
		}

		public String getEncoding() {
			return encoding;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public byte[] getByteData() {
			return byteData;
		}

		public void setByteData(byte[] byteData) {
			this.byteData = byteData;
		}
	}
}
