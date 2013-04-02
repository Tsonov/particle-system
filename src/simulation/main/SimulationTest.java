package simulation.main;

import java.awt.Color;
import java.io.*;

import simulation.particles.Particle;
import simulation.structures.ParticleSystem;
import simulation.outsideLibraries.StdIn;

public class SimulationTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if(args.length != 1) return;
		FileInputStream stream = new FileInputStream(args[0]);
		System.setIn(stream);
		int N = StdIn.readInt();
		Particle[] particles = new Particle[N];
		for (int i = 0; i < N; i++) {
			double rx = StdIn.readDouble();
			double ry = StdIn.readDouble();
			double vx = StdIn.readDouble();
			double vy = StdIn.readDouble();
			double radius = StdIn.readDouble();
			double mass = StdIn.readDouble();
			int r = StdIn.readInt();
			int g = StdIn.readInt();
			int b = StdIn.readInt();
			Color color = new Color(r, g, b);
			particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
		}

		ParticleSystem simulator = new ParticleSystem(particles);
		simulator.simulate(3000);
	}
}
