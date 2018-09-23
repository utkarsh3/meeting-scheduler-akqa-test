package meeting.scheduler.model;

import java.util.Date;

/**
 * Modal cLass for MeetingSchedule
 * 
 * @author utkarshgupta
 *
 */
public class MeetingSchedule {

	private long requestedDateTime;
	private String empId;
	private Date meetingDate;
	private String meetingStartTime;
	private String meetingEndTime;
	/* *Date in yyyy-mm-dd */
	private String requestedDate;

	/**
	 * @return the requestedDateTime
	 */
	public long getRequestedDateTime() {
		return requestedDateTime;
	}

	/**
	 * @param requestedDateTime
	 *            the requestedDateTime to set
	 */
	public void setRequestedDateTime(long requestedDateTime) {
		this.requestedDateTime = requestedDateTime;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the meetingDate
	 */
	public Date getMeetingDate() {
		return meetingDate;
	}

	/**
	 * @param meetingDate
	 *            the meetingDate to set
	 */
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	/**
	 * @return the meetingStartTime
	 */
	public String getMeetingStartTime() {
		return meetingStartTime;
	}

	/**
	 * @param meetingStartTime
	 *            the meetingStartTime to set
	 */
	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	/**
	 * @return the meetingEndTime
	 */
	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	/**
	 * @param meetingEndTime
	 *            the meetingEndTime to set
	 */
	public void setMeetingEndTime(String meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	/**
	 * @return the requestedDate
	 */
	public String getRequestedDate() {
		return requestedDate;
	}

	/**
	 * @param requestedDate
	 *            the requestedDate to set
	 */
	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MeetingSchedule [requestedDateTime=" + requestedDateTime + ", empId=" + empId + ", meetingDate="
				+ meetingDate + ", meetingStartTime=" + meetingStartTime + ", meetingEndTime=" + meetingEndTime
				+ ", requestedDate=" + requestedDate + "]";
	}
	

}
