package simulation.events;

import simulation.particles.Particle;

public class RedrawEvent extends AbstractEvent{
	public RedrawEvent(double time) {
		super(time);
	}

	@Override
	public boolean isValid() {
		//The draw event is always valid
		return true;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException("This event can't be executed directly");
	}

	@Override
	public Particle[] getParticlesInvolved() {
		throw new UnsupportedOperationException("This event can't be executed directly");
	}

}
