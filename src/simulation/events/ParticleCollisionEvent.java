package simulation.events;

import simulation.particles.Particle;

public class ParticleCollisionEvent extends AbstractEvent {
	private final Particle firstParticle, secondParticle;
	private final int collisionCountFirst, collisionCountSecond;

	public ParticleCollisionEvent(double time, Particle first, Particle second) {
		super(time);
		if (first == null || second == null) {
			throw new IllegalArgumentException("Particles can't be null!");
		}
		this.firstParticle = first;
		this.secondParticle = second;
		this.collisionCountFirst = first.collisionsCount();
		this.collisionCountSecond = second.collisionsCount();
	}

	@Override
	public boolean isValid() {
		if (firstParticle.collisionsCount() != collisionCountFirst
				|| secondParticle.collisionsCount() != collisionCountSecond) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		firstParticle.bounceOffParticle(secondParticle);
	}

	@Override
	public Particle[] getParticlesInvolved() {
		return new Particle[] { firstParticle, secondParticle };
	}

}
