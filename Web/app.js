(function () {
	var baseurl = 'http://localhost:9999';
	


  function getKurser() {
	   $.getJSON(`${baseurl}/kurs`, function (json) {
		for(var i = 0; i < json.length; i++) {
			var k = json[i];
			$('#studerande').append(`<ul><li>${k.KID}</li><li>${k.KursNamn}</li></ul>`);
		}
		
      console.info(json);
    }).fail(function () {
      console.warn('Anslutningen kunde ej upprättas.');
    });
  }
  
  function getKurs(kid) {
	  $.getJSON(`${baseurl}/kurs/${kid}`, function (json) {
		  debugger;
		for(var i = 0; i < json.length; i++) {
			var k = json[i];
			$('#studerande').append(`<ul><li>${k.KID}</li><li>${k.KursNamn}</li></ul>`);
		}
    }).fail(function () {
      console.warn('Anslutningen kunde ej upprättas.');
    });
	}
	
	function getUser(aid, password) {
	  $.getJSON(`${baseurl}/kurs/${kid}`, function (json) {
		  debugger;
		for(var i = 0; i < json.length; i++) {
			var k = json[i];
			$('#studerande').append(`<ul><li>${k.KID}</li><li>${k.KursNamn}</li></ul>`);
		}
    }).fail(function () {
      console.warn('Anslutningen kunde ej upprättas.');
    });
	}
  
  $(function (ready) {

		

		$('#loginButton').on('click', function(){
			var aid = $('#UserName').val();
			var password = $('#Password').val();
			$('.loginSection').hide();
			$('.userInfoSection').show();
			//getUser(aid, password);
		});

		$('#classes').on('click', function(){
			$('.userInfoSection').hide();
			$('.classesSection').show();
		});

		$('.className').on('click', function(){
			$('.classesSection').hide();
			$('.classInfoSection').show();
		});

		$('.classTask').on('click', function(){
			$('.classInfoSection').hide();
			$('.taskStudents').show();
		});

		$('.grading').on('click', function(){
			$('.classInfoSection').hide();
			$('.gradingSection').show();
		});
		

	$('#EIDbutton').on('click', function() {
		var kid = $('#EID').val();
		getKurs(kid);
	});
   
  });
 
})();