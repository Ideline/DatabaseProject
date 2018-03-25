(function () {
	var baseurl = 'http://localhost:9999';
	var user;
	var courses;
	var course;


	function hideAll() {
		var parts = ["loginSection", "userInfoSection", "studentSection", "studentInfoSection", "classesSection", "classInfoSection", "gradingSection", "taskStudents", "gradeSection"];
		for(var i = 0; i < parts.length; i++) {
			$('.' + parts[i]).hide();
		}
	}

	function getCourse() {
		$.getJSON(`${baseurl}/course`, function (json) {
			
			console.info(json);
		}).fail(function () {
			console.warn('Anslutningen kunde ej upprättas.');
		});
	}

	function getCourses() {
		$.getJSON(`${baseurl}/courses/${user.EID}`, function (json) {
			hideAll();
			courses = json;
			var coursesHtml = '';
			for(var i = 0; i < courses.length; i++) {
				var c = courses[i];
				coursesHtml += `<p class="className" data-cid="${c.CID}">${c.courseName} - ${c.CID} - ${c.courseCredits}</p>`;
			}
			$('.classesSection').html(coursesHtml);
			$('.className').each(function() {
				$(this).on('click', function() {
					getCourse($(this).attr('data-cid'));
				})
			});
			$('.classesSection').show();
		}).fail(function () {
			console.warn('Anslutningen kunde ej upprättas.');
		});
	}

	function getCourse(cid) {
		$.getJSON(`${baseurl}/course/${cid}`, function (c) {
			hideAll();
			$('#className2').val(`${c.courseName} - ${c.CID} - ${c.courseCredits}`);
			$('.classInfoSection').show();
		});
	}

	function getCourseParts(cid) {
		$.getJSON(`${baseurl}/course/${cid}/parts`, function (json) {
			hideAll();
			$('.gradingSection').show();
		});
	}

	function getUser(eid, password) {
		var possibleUser = localStorage.getItem('user');
		if(!possibleUser) {
			$.getJSON(`${baseurl}/user/${eid}/${password}`, function (json) {
				user = json;
				if (user) {
					hideAll();
					localStorage.setItem('user', JSON.stringify(user));
					showUserInfo();
				}
				else {
					alert("Felaktigt användarnamn eller lösenord!")
				}
			}).fail(function () {
				console.warn('Anslutningen kunde ej upprättas.');
			});
		} else {
			user = JSON.parse(possibleUser);
			showUserInfo();
		}
	}

	function showUserInfo() {
		$('.userInfoSection').html(
			`<div class="studSubDiv">
				<p>Personnummer: </p>
				<p>${user.EID}</p>
			</div>
			<div class="studSubDiv">
				<p>Förnamn: </p>
				<p>${user.fName}</p>
			</div>
			<div class="studSubDiv">
				<p>Efternamn: </p>
				<p>${user.lName}</p>
			</div>
			<div class="studSubDiv">
				<p>Adress: </p>
				<p>${user.sAddress},</p>
				<p>${user.zipCode}</p>
				<p>${user.city}</p>
			</div>
			<div class="studSubDiv">
				<p>Telefon: </p>
				<p>${user.phNumb}</p>
			</div>
			<div class="studSubDiv">
				<p>Epost: </p>
				<p>${user.email}</p>
			</div>`);
			$('.userInfoSection').show();
	}

	$(function (ready) {

		user = localStorage.getItem('user');
		if(user) {
			getUser(null, null);
		} else {
			$('.loginSection').show();
		}

		$('#loginButton').on('click', function () {
			var eid = $('#UserName').val();
			var password = $('#Password').val();
			user = getUser(eid, password);
		});

		$('#classes').on('click', function () {
			getCourses();
		});

		$('.className').on('click', function () {
			var cid = $(this).prop('data-cid');
			getCourse(cid);
		});

		$('.classTask').on('click', function () {
			$('.classInfoSection').hide();
			$('.taskStudents').show();
		});

		$('.grading').on('click', function () {
			$('.classInfoSection').hide();
			$('.gradingSection').show();
		});


		$('#EIDbutton').on('click', function () {
			var kid = $('#EID').val();
			getKurs(kid);
		});

	});

})();