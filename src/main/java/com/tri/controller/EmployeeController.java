package com.tri.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.tri.common.DropBox;
import com.tri.message.CustomMessageReceive;
import com.tri.model.Employee;
import com.tri.req.DownloadReq;
import com.tri.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private DropBox db;

	public EmployeeController() {
		db = new DropBox();
	}

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUser(@RequestHeader HttpHeaders header) {
		List<Employee> result = employeeService.findAll();
		if (result.isEmpty()) {
			return new ResponseEntity<>(new CustomMessageReceive("Fail", "Not found", null), HttpStatus.OK);
		}
		// this.writeFile(Arrays.asList("1. NKD", "2. DTTT", "3. NTD", "4. NTT"));
		return new ResponseEntity<>(new CustomMessageReceive("Success", null, result), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestHeader HttpHeaders header, @PathVariable("id") int id) {
		Employee existing = employeeService.findById(id);
		if (existing == null) {
			return new ResponseEntity<>(new CustomMessageReceive("Fail", "Not found", null), HttpStatus.OK);
		}
		/*
		 * HttpHeaders responseHeaders = new HttpHeaders();
		 * responseHeaders.set("Access-Control-Allow-Origin", "*");
		 * responseHeaders.set("Access-Control-Allow-Methods",
		 * "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		 * responseHeaders.set("Access-Control-Allow-Headers",
		 * "Origin, Content-Type, X-Auth-Token");
		 */
		return new ResponseEntity<>(new CustomMessageReceive("Success", null, existing), HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestHeader HttpHeaders header, @RequestBody Employee user) {
		if (employeeService.findById(user.getId()) != null) {
			return new ResponseEntity<>(new CustomMessageReceive("Fail", "This user have existed in database", null),
					HttpStatus.OK);
		}
		employeeService.save(user);
		return new ResponseEntity<>(new CustomMessageReceive("Success", "Create item complete", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestHeader HttpHeaders header, @PathVariable("id") int id,
			@RequestBody Employee user) {
		user.setId(id);
		return employeeService.update(user) != null
				? new ResponseEntity<>(new CustomMessageReceive("Success", "Update user complete", null), HttpStatus.OK)
				: new ResponseEntity<>(
						new CustomMessageReceive("Fail", "User " + user.getFirstName() + " not found", null),
						HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestHeader HttpHeaders header, @PathVariable("id") int id) {
		Employee user = employeeService.findById(id);
		employeeService.delete(id);
		return new ResponseEntity<>(
				new CustomMessageReceive("Success", "Delete user " + user.getFirstName() + " complete", null),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestHeader HttpHeaders header, @RequestBody DownloadReq req) {
		CustomMessageReceive rsp = new CustomMessageReceive();
		rsp.setCallStatus("Success");

		writeFile(req.getContent());

		DropBox.client = db.getInit();
		String msg = db.uploadFile(DropBox.client);
		if (!msg.isEmpty()) {
			rsp.setCallStatus("Fail");
		}
		rsp.setMessage(msg);

		return new ResponseEntity<>(rsp, HttpStatus.OK);
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ResponseEntity<?> download(@RequestHeader HttpHeaders header, @RequestBody DownloadReq dwl) {
		CustomMessageReceive rsp = new CustomMessageReceive();
		rsp.setCallStatus("Success");

		DropBox.client = db.getInit();
		String msg = db.downloadFile(DropBox.client, dwl.getFileName());
		if (!msg.isEmpty()) {
			rsp.setCallStatus("Fail");
		}
		rsp.setMessage(msg);

		return new ResponseEntity<>(rsp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUrl", method = RequestMethod.GET)
	public ResponseEntity<?> getUrl(@RequestHeader HttpHeaders header) {
		CustomMessageReceive rsp = new CustomMessageReceive();
		DropBox.client = db.getInit();
		rsp.setCallStatus("Success");
		rsp.setMessage("");

		try {
			List<String> res = db.getAllNameFile(DropBox.client);
			rsp.setResult(res);
		} catch (ListFolderErrorException e) {
			rsp.setCallStatus("Fail");
			rsp.setMessage(e.getMessage());
		} catch (DbxException e) {
			rsp.setCallStatus("Fail");
			rsp.setMessage(e.getMessage());
		}

		return new ResponseEntity<>(rsp, HttpStatus.OK);
	}

	private void writeFile(String content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(DropBox.FINAL_NAME))) {
			// Path file = Paths.get(DropBox.FINAL_NAME);
			// Files.write(file, Arrays.asList("1. NKD", "2. DTTT", "3. NTD", "4. NTT"),
			// Charset.forName("UTF-8"));
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}