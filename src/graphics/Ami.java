package graphics;



import game.racers.Racer;

public class Ami {
    private String RacerName;
    private double CurrentSpeed;
    private double MaxSpeed;
    private double CurrentX;
    private boolean Finished;
 
    public Ami(Racer racer) {
        super();
        this.RacerName=racer.getName();
        this.CurrentSpeed=racer.getCurrentSpeed();
        this.MaxSpeed=racer.getMaxSpeed();
        this.CurrentX=racer.getCurrentLocation().getX();
        this.Finished=racer.isfinish();
        
    }
    

	public String getRacerName() {
		return RacerName;
	}

	public void setRacerName(String racerName) {
		RacerName = racerName;
	}

	public double getCurrentSpeed() {
		return CurrentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		CurrentSpeed = currentSpeed;
	}

	public double getMaxSpeed() {
		return MaxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		MaxSpeed = maxSpeed;
	}

	public double getCurrentX() {
		return CurrentX;
	}

	public void setCurrentX(double currentX) {
		CurrentX = currentX;
	}

	public boolean isFinished() {
		return Finished;
	}

	public void setFinished(boolean finished) {
		Finished = finished;
	}
 
}