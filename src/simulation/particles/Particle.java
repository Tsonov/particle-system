package simulation.particles;

import java.awt.Color;

import simulation.outsideLibraries.StdDraw;

public class Particle {
	private static final double INFINITY = Double.POSITIVE_INFINITY;
	private double posX, posY;
	private double velocityX, velocityY;
	private double radius;
	private double mass;
	private Color color;
	private int collisionCount = 0;

	/**
	 * Creates a random particle in the [0.0, 1.0] coordinates range
	 */
	public Particle() {
		posX = Math.random();
		posY = Math.random();
		velocityX = 0.01 * (Math.random() - 0.2);
		velocityY = 0.01 * (Math.random() - 0.2);
		radius = 0.1 * Math.random();
		mass = 0.5;
		color = Color.BLACK;
	}

	/**
	 * Creates a particle with the given parameters.
	 * 
	 * @param positionX
	 *            X coordinate of the particle's center
	 * @param positionY
	 *            Y coordinate of the particle's center
	 * @param velocityX
	 *            X coordinate of the particle's next position
	 * @param velocityY
	 *            Y coordinate of the particle's next position
	 * @param radius
	 *            the radius of the particle
	 * @param mass
	 *            the mass of the particle
	 * @param color
	 *            the color of the particle for standard draw
	 */
	public Particle(double positionX, double positionY, double velocityX,
			double velocityY, double radius, double mass, Color color) {
		this.posX = positionX;
		this.posY = positionY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.radius = radius;
		this.mass = mass;
		this.color = color;
	}

	/**
	 * Returns the amount of collisions this particle was in
	 * 
	 * @return the collisions count
	 */
	public int collisionsCount() {
		return this.collisionCount;
	}

	/**
	 * Moves the particle to its new position over time
	 * 
	 * @param timeChange
	 *            the time during which the particle has moved
	 */
	public void move(double timeChange) throws Exception{
		posX += velocityX * timeChange;
		if(posX < 0 || posX > 1) {
			throw new Exception();
		}
		posY += velocityY * timeChange;
		if(posY < 0 || posY > 1) {
			throw new Exception();
		}
	}

	/**
	 * Draws the particle to the standard draw
	 */
	public void draw() {
		StdDraw.setPenColor(this.color);
		StdDraw.filledCircle(this.posX, this.posY, this.radius);
	}

	/**
	 * Calculates the time until this particle hits another particle
	 * 
	 * @param another
	 *            second particle in the collision
	 * @return the time until collision with the second particle
	 */
	public double timeToHitParticle(Particle another) {
		if (this == another) {
			return INFINITY;
		}
		// Physics, using common notations in case you check the formula
		double dx = another.posX - this.posX;
		double dy = another.posY - this.posY;
		double dVelX = another.velocityX - this.velocityX;
		double dVelY = another.velocityY - this.velocityY;
		double dvdr = dx * dVelX + dy * dVelY;
		if (dvdr > 0) {
			return INFINITY;
		}
		double dvdv = dVelX * dVelX + dVelY * dVelY;
		double drdr = dx * dx + dy * dy;
		double sigma = this.radius + another.radius;
		double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
		if (d < 0) {
			return INFINITY;
		}
		double result = -(dvdr + Math.sqrt(d)) / dvdv;
		return result;
	}

	/**
	 * Calculates the time until the particle hits a vertical wall
	 * 
	 * @return the time until collision with a vertical wall
	 */
	public double timeToHitVerticalWall() {
		if (this.velocityX > 0) {
			return (1.0 - this.posX - this.radius) / this.velocityX;
		} else if (velocityX < 0) {
			return (this.radius - this.posX) / this.velocityX;
		} else {
			return INFINITY;
		}
	}

	/**
	 * Calculates the time until the particle hits a horizontal wall
	 * 
	 * @return the time until collision with a horizontal wall
	 */
	public double timeToHitHorizontalWall() {
		if (this.velocityY > 0) {
			return (1.0 - posY - radius) / this.velocityY;
		} else if (this.velocityY < 0) {
			return (radius - this.posY) / this.velocityY;
		} else {
			return INFINITY;
		}
	}

	/**
	 * Makes calculations when a collision between two particles occurs
	 * 
	 * @param another
	 *            The second particle in the collision
	 */
	public void bounceOffParticle(Particle another) {
		double dx = another.posX - this.posX;
		double dy = another.posY - this.posY;
		double dVelX = another.velocityX - this.velocityX;
		double dVelY = another.velocityY - this.velocityY;
		double dvdr = dx * dVelX + dy * dVelY;
		double distanceBetween = this.radius + another.radius;

		// Calculate the force of impact
		double force = 2 * this.mass * another.mass * dvdr
				/ ((this.mass + another.mass) * distanceBetween);
		double forceX = force * dx / distanceBetween;
		double forceY = force * dy / distanceBetween;

		// Update velocities
		this.velocityX += forceX / this.mass;
		this.velocityY += forceY / this.mass;
		another.velocityX -= forceX / another.mass;
		another.velocityY -= forceY / another.mass;

		// One more collision for both particles, update
		this.collisionCount++;
		another.collisionCount++;
	}

	/**
	 * Makes calculations when a collision with a vertical wall occurs
	 */
	public void bounceOffVerticalWall() {
		this.velocityX = -velocityX;
		this.collisionCount++;
	}

	/**
	 * Makes calculations when a collision with a horizontal wall occurs
	 */
	public void bounceOffHorizontalWall() {
		this.velocityY = -velocityY;
		this.collisionCount++;
	}

	/**
	 * Returns the kinetic energy of this particle
	 * 
	 * @return the amount of kinetic energy this particle has
	 */
	public double kineticEnergy() {
		return 0.5
				* this.mass
				* (this.velocityX * this.velocityX + this.velocityY
						* this.velocityY);
	}
}
