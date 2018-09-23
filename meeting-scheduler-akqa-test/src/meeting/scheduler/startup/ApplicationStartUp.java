package meeting.scheduler.startup;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import meeting.scheduler.filereader.TextFileWriterReader;
import meeting.scheduler.flie.object.mapper.FileObjectMapper;
import meeting.scheduler.meetingrequestscheduler.MeetingRequestScheduler;
import meeting.scheduler.model.MeetingSchedule;

public class ApplicationStartUp {

	// Main Method to run the application directly
	public static void main(String args[]) {
		meetingScheduuler();
	}

	/**
	 * Main method which does all the file processing and writes the meeting
	 * schedule on to a file
	 */
	public static void meetingScheduuler() {
		String fileName = "resources/meeting-scheduler-test.txt";
		String ouputFileName = "resources/meeting-scheduler-output.txt";
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		TextFileWriterReader.write(classLoader, ouputFileName, meetingsRequestScheduler(fileName, classLoader));
		System.out.println("-----Output Has been Written on file");
		System.out.println("-----meeting-scheduler-output.txt----");

	}

	/**
	 * 
	 * @param fileName
	 * @param classLoader
	 */
	public static String meetingsRequestScheduler(String fileName, ClassLoader classLoader) {
		// reading the file into list of strings
		List<String> filesInputLines = TextFileWriterReader.readFile(classLoader, fileName);
		List<MeetingSchedule> meetingScheduleRequestList = FileObjectMapper
				.populateMeetingScheduleList(filesInputLines);
		System.out.println("-----File Has been Read-----");
		System.out.println();
		// meetingSchedule list will contain only those meetings request which
		// fall under official Hours

		if (CollectionUtils.isNotEmpty(meetingScheduleRequestList)) {
			/*
			 * Sorting meeting request list based upon the requested Date and
			 * Time so as to give higher priority to those who requested first
			 * for meeting schedule
			 */
			meetingScheduleRequestList.sort(Comparator.comparing(MeetingSchedule::getRequestedDateTime));

			/*
			 * Preparing the final List of scheduled meetings by removing those
			 * request which are overlapping and were requested after the first
			 * requested had already booked the slot
			 */
			return MeetingRequestScheduler.scheduleMeeeting(meetingScheduleRequestList);

		}
		return StringUtils.EMPTY;
	}
}
