package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class HelloWorld {
	private static EV3 ev3 = (EV3) BrickFinder.getLocal();
	
	public static void main(String[] args) throws InterruptedException {
		//testSensorAndMotor();
		dolak();
	}
	
	public static void testMotor() {
		TextLCD lcd = ev3.getTextLCD();
		Keys keys = ev3.getKeys();
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		
		m.forward();
		Delay.msDelay(10000);
	}
	
	public static void testSensor() {
		EV3GyroSensor ev3GyroSensor = new EV3GyroSensor(SensorPort.S4);
		
		for(int i = 0; i < 2000; i++) {
			Delay.msDelay(100);
			SampleProvider angleAndRateMode = ev3GyroSensor.getAngleAndRateMode();
			float[] floats = new float[angleAndRateMode.sampleSize()];
			angleAndRateMode.fetchSample(floats, 0);
			System.out.println("Iteration: " + i);
			System.out.println("Gyro angle: " + floats[0]);
		}
	}
	
	public static void testSensorAndMotor() {
		EV3GyroSensor ev3GyroSensor = new EV3GyroSensor(SensorPort.S4);
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		
		m.forward();
		
		for(int i = 0; i < 2000; i++) {
			Delay.msDelay(100);
			SampleProvider angleAndRateMode = ev3GyroSensor.getAngleAndRateMode();
			float[] floats = new float[angleAndRateMode.sampleSize()];
			angleAndRateMode.fetchSample(floats, 0);
			System.out.println("Iteration: " + i);
			System.out.println("Gyro angle: " + floats[0]);
			if(floats[0] > 0.0) {
				m.setSpeed((int)Math.max(0, floats[0] * 1.3));
			}
		}
	}
	
	public static void dolak() {
		EV3GyroSensor ev3GyroSensor = new EV3GyroSensor(SensorPort.S4);
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		
		m.forward();
		
		for(int i = 0; i < 2000; i++) {
			int tun = 80;
			int dragon = 50;
			Delay.msDelay(100);
			SampleProvider angleAndRateMode = ev3GyroSensor.getAngleAndRateMode();
			float[] floats = new float[angleAndRateMode.sampleSize()];
			angleAndRateMode.fetchSample(floats, 0);
			System.out.println("Iteration: " + i);
			System.out.println("Gyro angle: " + floats[0]);
			
			m.setSpeed((int)Math.max(0, floats[0] * 1.3));
			if(floats[0] > 0.0) {
				m.setSpeed((int)Math.max(tun, floats[0] * dragon + tun));
				m.forward();
			} else if (floats[0] < 0.0) {
				m.setSpeed((int)Math.max(tun, floats[0] * dragon * -1 + tun));
				m.backward();
			}
		}
	}
}