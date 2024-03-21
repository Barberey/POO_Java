package chrono;

/**
 * The bounded counter has a min value and a max value.
 * 
 * We can set freely these two values when creating the bounded counter.
 * 
 * @author Tianxiao.Liu@u-cergy.fr
 **/
public class BoundedCounter extends Counter {
	private int max;
	private int min;

	public BoundedCounter(int value, int max, int min) {
		super(value);
		this.max = max;
		this.min = min;
	}

	@Override
	public void decrement() {
		if (getValue() > min) {
			super.decrement();
		}
	}

	@Override
	public void incrementS() {
		if (getValue() < max) {
			super.incrementS();
		}
	}
	
	@Override
	public void incrementM() {
		if (getValue() < max) {
			super.incrementM();
		}
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

}
