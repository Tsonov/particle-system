package simulation.events;

import simulation.particles.Particle;

public class HitVerticalWallEvent extends AbstractEvent {
	private final Particle particle;
	private final int collisionCount;

	public HitVerticalWallEvent(double time, Particle particle) {
		super(time);
		if (particle == null) {
			throw new IllegalArgumentException("Particle can't be null");
		}
		this.particle = particle;
		this.collisionCount = particle.collisionsCount();
	}

	@Override
	public boolean isValid() {
		if (this.particle.collisionsCount() != this.collisionCount) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		particle.bounceOffVerticalWall();
	}

	@Override
	public Particle[] getParticlesInvolved() {
		return new Particle[] { particle };
	}

}
