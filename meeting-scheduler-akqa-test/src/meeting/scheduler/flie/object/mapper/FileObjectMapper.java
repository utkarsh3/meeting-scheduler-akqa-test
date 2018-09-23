package meeting.scheduler.flie.object.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import meeting.scheduler.model.MeetingSchedule;
import meeting.scheduler.model.OfficialHours;
import meeting.scheduler.util.DateTimeConversionUtils;

/**
 * This class act as a mapper for mapping object from List<> lines to
 * MeetingSchedule list
 * 
 * @author utkarshgupta
 *
 */
public final class FileObjectMapper {

	private FileObjectMapper() {

	}

//	private static final String regex = "(\\d{4}-\\d{2}-\\d{2})\\s(\\d{2}:\\d{2}:\\d{2})\\s([a-zA-Z0-9]+)(\\d{4}-\\d{2}-\\d{2})\\s(\\d{2}:\\d{2})\\s(\\d{1})";

	/**
	 * This method populate the Value from the List of lines read from file to
	 * the {@OfficialHours} and List<MeetingSchedule>
	 * 
	 * @param fileLinesList
	 * @return
	 */
	public static List<MeetingSchedule> populateMeetingScheduleList(List<String> fileLinesList) {
		// :TODO Assumption that file in submitted in right order and right
		// format
		/*
		 * Assumption that file in submitted in right order and right spaces
		 * 
		 */
		if (CollectionUtils.isNotEmpty(fileLinesList)) {
			String officialTime = fileLinesList.get(0);
			// Populate official Hours
			OfficialHours officialHours = new OfficialHours();
			populateOfficialHours(officialHours, officialTime);
			// Populate Meeting schedule Request List
			List<MeetingSchedule> meetingScheduleRequestList = populateMeetingListFromFileLinesList(fileLinesList,
					officialHours);
			return meetingScheduleRequestList;
		}
		return new ArrayList<>();
	}

	/**
	 * 
	 * @param fileLinesList
	 * @param officialHours
	 * @return
	 */
	private static List<MeetingSchedule> populateMeetingListFromFileLinesList(List<String> fileLinesList,
			OfficialHours officialHours) {
		int listsize = fileLinesList.size();
		// The 0th element in lineList contains official Hours so we are
		// starting our iteration from 1 to populate
		// meetingScheduleRequestList
		List<MeetingSchedule> meetingScheduleRequestList = new ArrayList<>();
		for (int i = 1; i < listsize; i += 2) {
			// Increasing each iteration by 2 as the meeting request is given in
			// two subsequent lines
			// eg: 2011-03-17 10:17:06 EMP001
			// 2011-03-21 09:00 2
			meetingScheduleObject(meetingScheduleRequestList, fileLinesList.get(i), fileLinesList.get(i + 1),
					officialHours);
		}
		return meetingScheduleRequestList;
	}

	/**
	 * Mapping each value in a line to its corresponding attribute in
	 * MeetingSchedule object
	 * 
	 * @param meetingList
	 * @param requestDateAndEmployee
	 * @param meetingDateAndHours
	 * @param officialHours
	 */
	private static void meetingScheduleObject(List<MeetingSchedule> meetingList, String requestDateAndEmployee,
			String meetingDateAndHours, OfficialHours officialHours) {
		MeetingSchedule meetingScheduleObject = new MeetingSchedule();
		// Below operation Separates the employee ID and request date eg
		// value of requestDateAndEmployee= 2011-03-16 12:34:56 EMP002
		int lastSpaceIndex = StringUtils.lastIndexOf(requestDateAndEmployee, " ");
		String requestDate = StringUtils.trim(StringUtils.substring(requestDateAndEmployee, 0, lastSpaceIndex));
		String employeeID = StringUtils.trim(
				StringUtils.substring(requestDateAndEmployee, lastSpaceIndex + 1, requestDateAndEmployee.length()));

		Date formattedMeetingRequestDate = DateTimeConversionUtils.formatDateFullDate(requestDate);
		meetingScheduleObject
				.setRequestedDateTime(formattedMeetingRequestDate == null ? 0 : formattedMeetingRequestDate.getTime());
		meetingScheduleObject.setEmpId(employeeID);
		

		// Below operation Separates the meeting day and time into meeting date,
		// starttime and endtime
		// value of meetingDateAndHours= 2011-03-21 09:00 2
		String[] meetingDate = StringUtils.split(meetingDateAndHours, " ");
		meetingScheduleObject
				.setMeetingDate(DateTimeConversionUtils.formatDateyyyy_mm_dd(StringUtils.trim(meetingDate[0])));
		meetingScheduleObject.setMeetingStartTime(DateTimeConversionUtils.convertIntoMilataryTime(meetingDate[1]));
		meetingScheduleObject.setMeetingEndTime(getMeetingEndTime(meetingScheduleObject, meetingDate));
		// Setting requested date also as string of yyyy-mm-dd here requestDate
				// = 2011-03-16 12:34:56
				meetingScheduleObject.setRequestedDate(StringUtils.trim(meetingDate[0]));
		addValueToList(meetingList, meetingScheduleObject, officialHours);
	}

	/**
	 * This method Will add only those value to List for whom the meeting start
	 * and End Date is inside the working hours of the company
	 * 
	 * // Check if the meeting is falling Under office working hours than only
	 * add it to meeting schedule list
	 * 
	 * @param meetingList
	 * @param meetingScheduleObject
	 * @param officialHours
	 */
	private static void addValueToList(List<MeetingSchedule> meetingList, MeetingSchedule meetingScheduleObject,
			OfficialHours officialHours) {
		// Check if the meeting is falling Under office working hours
		int officialStartTime = Integer.parseInt(officialHours.getStartTime());
		int officialEndTime = Integer.parseInt(officialHours.getEndTime());
		int meetingStartTime = Integer.parseInt(meetingScheduleObject.getMeetingStartTime());
		int meetingEndTime = Integer.parseInt(meetingScheduleObject.getMeetingEndTime());
		if (meetingStartTime >= officialStartTime && meetingEndTime <= officialEndTime) {
			meetingList.add(meetingScheduleObject);
		}
	}

	/**
	 * Setting meeting end date The endMeeting time will be startTime + no of
	 * hours*100 in military time format eg startTime 0900 and hours is 2 than
	 * endTime will be 1100
	 * 
	 * @param meetingScheduleObject
	 * @param meetingDate
	 * @return
	 */
	private static String getMeetingEndTime(MeetingSchedule meetingScheduleObject, String[] meetingDate) {
		// The endMeeting time will be startTime + no of hours*100 in military
		// time format
		// eg startTime 0900 and hours is 2 than endTime will be 1100
		int endDateMilitaryForm = Integer.parseInt(meetingScheduleObject.getMeetingStartTime())
				+ Integer.parseInt(meetingDate[2]) * 100;
		return String.valueOf(endDateMilitaryForm);
		// TODO: handle case in which endTime is more than 23:59 hrs
	}

	/**
	 * This method populate the official Working Hours to
	 * the @link{OfficialHours} Object
	 * 
	 * @param officialHours
	 * @param officialTime
	 */
	private static void populateOfficialHours(OfficialHours officialHours, String officialTime) {
		officialHours
				.setEndTime(StringUtils.substring(officialTime, StringUtils.lastIndexOf(officialTime, " ")).trim());
		officialHours.setStartTime(
				StringUtils.substring(officialTime, 0, StringUtils.lastIndexOf(officialTime, " ")).trim());
	}
}
