package simulation.events;

import simulation.particles.Particle;

public abstract class AbstractEvent implements Comparable<AbstractEvent> {
	public final double time;

	protected AbstractEvent(double time) {
		this.time = time;
	}

	public int compareTo(AbstractEvent another) {
		return Double.compare(this.time, another.time);
	}

	public abstract boolean isValid();
	
	public abstract void execute();
	
	public abstract Particle[] getParticlesInvolved();
}
