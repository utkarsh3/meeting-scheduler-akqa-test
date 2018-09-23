package meeting.scheduler.model;

public class OfficialHours {

	private String startTime;

	@Override
	public String toString() {
		return "OfficialHours [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
