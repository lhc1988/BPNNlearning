package lab.cgcl.aliBigdata.BPNNlearning.domain;

public class UserBrandPair {
	
	private String user_id;
	
	private String brand_id;
	
	/**
	 * 特征向量
	 */
	private double[] vector;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public double[] getVector() {
		return vector;
	}

	public void setVector(double[] vector) {
		this.vector = vector;
	}

}
