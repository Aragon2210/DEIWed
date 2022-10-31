package pt.ulisboa.tecnico.rnl.dei.deiwed.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.AttendeeDto;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.service.AttendeeService;

@RestController
public class DeiwedController {
	@Autowired
	private AttendeeService attendeeService;

	@GetMapping("/attendees")
	public List<AttendeeDto> getAttendees() {
		return attendeeService.getAllAttendees();
	}

	@PostMapping("/attendees")
	public AttendeeDto createAttendee(@RequestBody AttendeeDto attendeeDto) {
		return attendeeService.createAttendee(attendeeDto);
	}

	@GetMapping("/attendees/{id}")
	public AttendeeDto getAttendee(@PathVariable long id) {
		return attendeeService.getAttendee(id);
	}

	@PutMapping("/attendees/{id}")
	public AttendeeDto updateAttendee(@PathVariable long id, @RequestBody AttendeeDto attendeeDto) {
		return attendeeService.updateAttendee(id, attendeeDto);
	}

	@DeleteMapping("/attendees/{id}")
	public void deleteAttendee(@PathVariable long id) {
		attendeeService.deleteAttendee(id);
	}
}
