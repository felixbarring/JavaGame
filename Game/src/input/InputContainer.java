package input;

import java.util.Map;

public class InputContainer {

	public final Map<InputAction, Integer> inputActions;
	public final int[] mouseLocation;
	
	InputContainer(Map<InputAction, Integer> k, int[] m ){
		inputActions = k;
		mouseLocation = m;
	}
	
}
