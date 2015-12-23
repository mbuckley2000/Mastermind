/**
 * Created by matt on 13/12/2015.
 */
public class AI implements Player {
	AIInterface aiInterface;

	public AI(String name) {
		aiInterface = new AIInterface(name);
	}

	public Interface getInterface() {
		return(aiInterface);
	}
}
