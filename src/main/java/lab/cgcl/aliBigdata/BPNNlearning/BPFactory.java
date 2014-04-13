package lab.cgcl.aliBigdata.BPNNlearning;

public class BPFactory {
	
//	private BP bp;
	
	private int inputSize, hiddenSize, outputSize;
	
	public BP build() {
		BP bp = new BP(inputSize, hiddenSize, outputSize);
		return bp;
	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public int getHiddenSize() {
		return hiddenSize;
	}

	public void setHiddenSize(int hiddenSize) {
		this.hiddenSize = hiddenSize;
	}

	public int getOutputSize() {
		return outputSize;
	}

	public void setOutputSize(int outputSize) {
		this.outputSize = outputSize;
	}
	
	
	


}
