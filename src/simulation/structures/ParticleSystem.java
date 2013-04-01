package simulation.structures;

import simulation.events.*;
import simulation.outsideLibraries.*;
import simulation.particles.Particle;

public class ParticleSystem {
	private final int redrawWaitMilliseconds = 20;
	private MinPriorityQueue<AbstractEvent> events;
	private double currentTime = 0.0;
	private double redrawFrequency = 8;
	private Particle[] particles;

	public ParticleSystem(Particle[] particles) {
		this.particles = particles;
		StdDraw.setXscale(1.0 / 22.0, 21.0 / 22.0);
		StdDraw.setYscale(1.0 / 22.0, 21.0 / 22.0);
	}

	public void simulate(double limitTime) throws Exception {
		StdDraw.show(0);
		//Initialize and draw the starting locations
		events = new MinPriorityQueue<>();
		currentTime = 0;
		for (int i = 0; i < this.particles.length; i++) {
			predict(particles[i], limitTime);
		}
		events.insert(new RedrawEvent(currentTime));
		
		while (events.isEmpty() == false) {
			AbstractEvent event = events.removeMin();
			if(event.isValid() == false) {
				continue;
			}
			for (int i = 0; i < this.particles.length; i++) {
				particles[i].move(event.time - currentTime);
			}
			currentTime = event.time;
			if(event instanceof RedrawEvent) {
				this.redraw(limitTime);
			} else {
				event.execute();
				for (Particle particle : event.getParticlesInvolved()) {
					predict(particle, limitTime);
				}
			}
		}
	}
	
	private void predict(Particle particle, double limitTime) {
		if (particle == null) {
			return;
		}

		// Check for collisions with other particles
		for (int i = 0; i < particles.length; i++) {
			double timeInterval = particle.timeToHitParticle(particles[i]);
			if (currentTime + timeInterval <= limitTime) {
				events.insert(new ParticleCollisionEvent(currentTime
						+ timeInterval, particle, particles[i]));
			}
		}

		// Check for collisions with walls
		double timeToHitVertical = particle.timeToHitVerticalWall();
		double timeToHitHorizontal = particle.timeToHitHorizontalWall();
		if (currentTime + timeToHitVertical <= limitTime) {
			events.insert(new HitVerticalWallEvent(currentTime
					+ timeToHitVertical, particle));
		}
		if (currentTime + timeToHitHorizontal <= limitTime) {
			events.insert(new HitHorizontalWallEvent(currentTime
					+ timeToHitHorizontal, particle));
		}
	}
	
	private void redraw(double limitTime) {
		StdDraw.clear();
		for (Particle particle : this.particles) {
			particle.draw();
		}
		StdDraw.show(redrawWaitMilliseconds);
		if(currentTime < limitTime) {
			events.insert(new RedrawEvent(currentTime + 1.0 / redrawFrequency));
		}
	}
	
	
}
