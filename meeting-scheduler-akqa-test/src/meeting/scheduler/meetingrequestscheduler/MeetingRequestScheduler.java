package meeting.scheduler.meetingrequestscheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import meeting.scheduler.model.MeetingSchedule;
import meeting.scheduler.util.DateTimeConversionUtils;

/**
 * This Class checks for the overlapping meetings and generates the meeting list
 * which are acceptable
 * 
 * @author utkarshgupta
 *
 */
public final class MeetingRequestScheduler {

	/**
	 * This method populates each meeting schedule object against a map and than
	 * removes the overlapping meeting request and priority is given those
	 * request which were submitted earliest
	 * 
	 * @param meetingScheduleRequestList
	 * @return
	 */
	public static String scheduleMeeeting(List<MeetingSchedule> meetingScheduleRequestList) {

		Map<String, List<MeetingSchedule>> meetingScheduleMap = new HashMap<>();
		Map<String, List<MeetingSchedule>> updatedMap = new HashMap<>();
		populateMeetingScheduleMap(meetingScheduleRequestList, meetingScheduleMap);
		for (Entry<String, List<MeetingSchedule>> entryObject : meetingScheduleMap.entrySet()) {
			// Adding the list against each date to form the final Accepted
			// meeting Map after removing duplicates
			updatedMap.put(entryObject.getKey(), removeOverLappingMeetingFromList(entryObject.getValue()));

		}
		return buildMeetingSchedule(updatedMap);
	}

	/**
	 * * This method resolve the meeting scheduling in case of overlapping dates
	 * and time
	 * 
	 * @param meetingScheduleList
	 */
	private static List<MeetingSchedule> removeOverLappingMeetingFromList(List<MeetingSchedule> meetingScheduleList) {
		List<MeetingSchedule> acceptedMeetingList = new ArrayList<>();
		acceptedMeetingList.add(meetingScheduleList.get(0));
		for (int i = 1; i < meetingScheduleList.size(); i++) {
			boolean flag = true;
			for (int j = 0; j < acceptedMeetingList.size(); j++) {
				int latestMeetingStartDate = Integer.parseInt(acceptedMeetingList.get(j).getMeetingStartTime());
				int latestMeetingEndDate = Integer.parseInt(acceptedMeetingList.get(j).getMeetingEndTime());
				int newMeetingStartDate = Integer.parseInt(meetingScheduleList.get(i).getMeetingStartTime());
				int newMeetingEndDate = Integer.parseInt(meetingScheduleList.get(i).getMeetingEndTime());
				// Checking the meeting for overlapping dates
				if (newMeetingStartDate == latestMeetingStartDate && newMeetingEndDate == latestMeetingEndDate) {
					flag = false;
				} else if (newMeetingStartDate < latestMeetingStartDate && newMeetingEndDate > latestMeetingStartDate) {
					flag = false;
				} else if (newMeetingStartDate > latestMeetingStartDate && newMeetingStartDate < latestMeetingEndDate) {
					flag = false;
				}

			}
			if (flag) {
				acceptedMeetingList.add(meetingScheduleList.get(i));
			}
		}
		return acceptedMeetingList;

	}

	/**
	 * Creating a map in which key is the meetingSchedule date and value is the
	 * list of all meeting scheduled for that day
	 * 
	 * @param meetingScheduleRequestList
	 * @param meetingScheduleMap
	 */
	private static void populateMeetingScheduleMap(List<MeetingSchedule> meetingScheduleRequestList,
			Map<String, List<MeetingSchedule>> meetingScheduleMap) {
		for (MeetingSchedule meetingSchedule : meetingScheduleRequestList) {
			// Creating a map in which key is the meetingSchedule date and value
			// is the list of all meeting scheduled for that day
			if (meetingScheduleMap.get(meetingSchedule.getRequestedDate()) == null) {
				List<MeetingSchedule> meetingSameDayList = new ArrayList<>();
				meetingSameDayList.add(meetingSchedule);
				meetingScheduleMap.put(meetingSchedule.getRequestedDate(), meetingSameDayList);
			} else {
				meetingScheduleMap.get(meetingSchedule.getRequestedDate()).add(meetingSchedule);
			}
		}
	}

	/**
	 * Converting the
	 * 
	 * @param meetingScheduleMap
	 * @return
	 */
	private static String buildMeetingSchedule(Map<String, List<MeetingSchedule>> meetingScheduleMap) {
		StringBuilder outputForMeetingSchedule = new StringBuilder();
		for (Map.Entry<String, List<MeetingSchedule>> meetingEntry : meetingScheduleMap.entrySet()) {
			String meetingDate = meetingEntry.getKey();
			outputForMeetingSchedule.append(meetingDate).append("\n");
			for (MeetingSchedule meetingSchedule : meetingEntry.getValue()) {
				outputForMeetingSchedule.append(
						DateTimeConversionUtils.convertTwentyFoursHoursTime(meetingSchedule.getMeetingStartTime()))
						.append(" ");
				outputForMeetingSchedule.append(
						DateTimeConversionUtils.convertTwentyFoursHoursTime(meetingSchedule.getMeetingEndTime()))
						.append(" ");
				outputForMeetingSchedule.append(meetingSchedule.getEmpId()).append("\n");
			}

		}
		System.out.println("Scheduled Meeting"+"\n" + outputForMeetingSchedule);
		return outputForMeetingSchedule.toString();

	}
}