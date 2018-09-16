package com.toobei.common.entity;

public class ImageResponseEntity extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2995196623960189899L;

	private boolean ret;

	private ImageResponseInfo info;

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public ImageResponseInfo getInfo() {
		return info;
	}

	public void setInfo(ImageResponseInfo info) {
		this.info = info;
	}

	public class ImageResponseInfo extends BaseEntity {

		/** serialVersionUID*/
		private static final long serialVersionUID = 5969941651305539980L;
		private String md5;
		private String size;

		public String getMd5() {
			return md5;
		}

		public void setMd5(String md5) {
			this.md5 = md5;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

	}

}
