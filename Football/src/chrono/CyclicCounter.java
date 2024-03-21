package chrono;

/**
 * The cyclic counter is a bounded counter with cyclic value change.
 * 
 * @author Tianxiao.Liu@u-cergy.fr
 **/
public class CyclicCounter extends BoundedCounter {

	public CyclicCounter(int value, int max, int min) {
		super(value, max, min); 
	}

	@Override
	public void decrement() {
		if (getValue() > getMin()) {
			super.decrement();
		} else {
			setValue(getMax());
		}
	}

	@Override
	public void incrementS() {
		if (getValue() < getMax()) {
			super.incrementS();
		} else {
			setValue(getMin());
		}
	}
	
	@Override
	public void incrementM() {
		if (getValue() < getMax()) {
			super.incrementM();
		} else {
			setValue(getMin());
		}
	}

	@Override
	public String toString() {
		return Chronometer.transform(getValue());
	}

}
