package controller;

import org.jfree.util.Log;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import persistence.Genome;

public class BalanceControllerImplementation extends Controller{

	private static EV3 brick = (EV3) BrickFinder.getLocal();
	private static RegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.A);
	private static EV3GyroSensor gyroScope = new EV3GyroSensor(SensorPort.S4);
	private static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S3);
	private static SampleProvider gyroScopeProvider = gyroScope.getAngleAndRateMode();
	private static SampleProvider touchSensorProvider = touchSensor.getTouchMode();
	private static float[] gyroScopeSample = new float[gyroScopeProvider.sampleSize()];
	private static float[] touchSensorSample = new float[touchSensor.sampleSize()];
	
	private static int speedZeroise = 3;
	private static int timeToLiftMs = 1000;
	private static int stepMs = 50; // Readjust 20 times per second
	private static float angleZeroise = 0.75f;
	private static float speedAmplification = 10.0f;

	@Override
	public long run() {
		Long startMs = System.currentTimeMillis();
		boolean didLift = false;
		boolean isPressed = true;
		boolean hasTimeToLift = true;
		
		Log.debug("Initializing run on genome: " + getGenome());
		
		while ((hasTimeToLift && !didLift) || inAir()) {
			float[] angleAndVelocity = getAngleAndVelocity();
			float modifiedAngle = angleAndVelocity[0] + (float) getGenome().getGenes()[0];
			float desiredMotorSpeed = getDesiredMotorSpeedByGenome(modifiedAngle, angleAndVelocity[0]);
			rotateMotor(Math.round(desiredMotorSpeed), modifiedAngle > 0);
			isPressed = isPressed();
			hasTimeToLift = hasTimeToLift(startMs);
			if(inAir()) {
				if(didLift == false) {
					Log.debug("EV3 is mid air!");
				}
				didLift = true;
			}
		}
		
		long fitness = 0;
		if(!didLift) {
			fitness = 0;
		} else {
			fitness = System.currentTimeMillis() - startMs;
			if(fitness < 0) {
				throw new IllegalStateException("Fitness cannot be negative if EV3 did lift");
			}
		}
		
		Log.debug("Fitness for genome: " + getGenome().toString() + " was " + fitness);
		return fitness;
	}
	
	private float getDesiredMotorSpeedByGenome(float angle, float angularVelocity) {
		float[] genes = getGenome().getGenes();
		
		float a = genes[1] * speedAmplification;
		float b = genes[2] * speedAmplification;
		float c = genes[3] * speedAmplification;
		
		float flatSpeed = 0.0f;
		float linearSpeed = 0.0f;
		float linearSpeedVelocity = 0.0f;
		
		if(angle < angleZeroise) {
			flatSpeed = 0.0f;
		} else {
			flatSpeed = a;
		}
		linearSpeed = b * angle;
		linearSpeedVelocity = c * angularVelocity;
		
		return flatSpeed + linearSpeed + linearSpeedVelocity;
	}
	
	private boolean hasTimeToLift(Long startMs) {
		return System.currentTimeMillis() - startMs < timeToLiftMs;
	}
	
	private void rotateMotor(int speed, boolean clockwise) {
		if(speed <= speedZeroise) {
			speed = 0;
		}
		if(speed == 0) {
			motor.stop();
		} else if(clockwise) {
			motor.forward();
			motor.setSpeed(speed);
		} else {
			motor.backward();
			motor.setSpeed(speed);
		}
	}
	
	private float[] getAngleAndVelocity() {
		gyroScopeProvider.fetchSample(gyroScopeSample, 0);
		return gyroScopeSample;
	}
	
	private boolean inAir() {
		return !isPressed();
	}
	
	private boolean isPressed() {
		touchSensorProvider.fetchSample(touchSensorSample, 0);
		if(touchSensorSample[0] == 0.0) {
			return false;
		}
		if(touchSensorSample[0] == 1.0) {
			return true;
		}
		throw new IllegalStateException();
	}
}
