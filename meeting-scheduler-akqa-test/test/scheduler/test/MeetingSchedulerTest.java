package scheduler.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import meeting.scheduler.flie.object.mapper.FileObjectMapper;
import meeting.scheduler.meetingrequestscheduler.MeetingRequestScheduler;
import meeting.scheduler.model.MeetingSchedule;
import meeting.scheduler.startup.ApplicationStartUp;

public class MeetingSchedulerTest {

	@Test
	public void testPopulateObjectMapperClass() {
		List<String> request = Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2");
		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.get(0).getEmpId(), "EMP001");
		Assert.assertEquals(list.get(0).getRequestedDate(), "2011-03-21");
		Assert.assertEquals(list.get(0).getMeetingStartTime(), "0900");

	}

	@Test
	public void testMeetingRequestSchedulerForOverLappingMeeting() {
		List<String> request = Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2",
				"2011-03-16 12:34:56 EMP002", "2011-03-21 10:00 1");
		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);
		String meeeting = MeetingRequestScheduler.scheduleMeeeting(list);
		Assert.assertNotNull(meeeting);
		Assert.assertEquals(meeeting, "2011-03-21\n" + "09:00 11:00 EMP001\n");

	}

	@Test
	public void testWITHBLANKFILE() {
		List<String> request = Arrays.asList("");
		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);
		String meeeting = MeetingRequestScheduler.scheduleMeeeting(list);
		Assert.assertNotNull(meeeting);
		Assert.assertEquals(meeeting, "");
	}

	@Test
	public void testGroupMeetingsChronologically() {
		List<String> request = Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP004", "2011-03-22 16:00 1",
				"2011-03-16 09:28:23 EMP003", "2011-03-22 14:00 2");
		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);

		String formattedMeeting = ApplicationStartUp.meetingsRequestScheduler("resources/testcases/testcasesused.txt",
				ClassLoader.getSystemClassLoader());
		Assert.assertNotNull(formattedMeeting);
		Assert.assertNotNull(list);

		Assert.assertEquals(formattedMeeting, "2011-03-22\n" + "14:00 16:00 EMP003\n" + "16:00 17:00 EMP004\n");

	}

	@Test
	public void testMeetingsShouldNotOverlap() {
		List<String> request = Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2",
				"2011-03-16 12:34:56 EMP002", "2011-03-21 10:00 1");

		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);
		String formattedMeeting = ApplicationStartUp.meetingsRequestScheduler(
				"resources/testcases/overlaptestcasefile.txt", ClassLoader.getSystemClassLoader());
		Assert.assertNotNull(formattedMeeting);
		Assert.assertNotNull(list);

		Assert.assertEquals(formattedMeeting, "2011-03-21\n" + "10:00 11:00 EMP002\n");

	}

	@Test
	public void invalidInputDataShouldEndWithNull() {
		List<String> request = Arrays.asList("0900 1730" + "2011-03-17 10:17:06 EMP001");
		List<MeetingSchedule> list = FileObjectMapper.populateMeetingScheduleList(request);
		Assert.assertNotNull(list);
		Assert.assertEquals(list, new ArrayList<>());
	}

}
