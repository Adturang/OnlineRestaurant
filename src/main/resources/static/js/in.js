function dt(){
	document.getElementById('demo').innerHTML = Date();
}
function logValid(){
	var username=document.forms["log"]["userName"]
	var pass1=document.forms["log"]["password"]
	var er1=document.getElementById("err")
	if (username.value=="") {
	  er1.textContent="*Please Enter UserName*"
	  er1.style.color="red"
	  username.focus();
	  return false;
	}
	if (pass1.value=="") {
	  er1.textContent="*Please Enter Password*"
	  er1.style.color="red"
	  pass1.focus();
	  return false;
	}
}

function validate() {
	var name=document.forms["regis"]["name"]
	var email=document.forms["regis"]["email"]
	var mob=document.forms["regis"]["number"]
	var pass1=document.forms["regis"]["password"]
	var pass2=document.forms["regis"]["cnfpass"]
	var er1=document.getElementById("err")
	var adr=document.forms["regis"]["address"]
	var username=document.forms["regis"]["userName"]
	
	
	if(name.value==""){
		er1.textContent="*please Enter Name*"
		er1.style.color="red";
		name.focus();
		return false;
	}
		if (mob.value=="") {
		  er1.textContent="*Please Enter Mobile Number*"
		  er1.style.color="red"
		  mob.focus();
		  return false;
	}
			if (email.value=="") {
		er1.textContent="*Please Enter Your Email"
		er1.style.color="red"
		email.focus();
		return false;
	}
	
		if (adr.value=="") {
		  er1.textContent="*Please Enter Address*"
		  er1.style.color="red"
		  adr.focus();
		  return false;
	}

	if (mob.value=="") {
		  er1.textContent="*Please Enter Mobile Number*"
		  er1.style.color="red"
		  mob.focus();
		  return false;
	}
	if (username.value=="") {
	  er1.textContent="*Please Choose A UserName For Your-Self*"
	  er1.style.color="red"
	  username.focus();
	  return false;
	}
	if (pass1.value=="") {
	  er1.textContent="*Please Create Password*"
	  er1.style.color="red"
	  pass1.focus();
	  return false;
	}
		if (pass2.value!==pass1.value) {
	  er1.textContent="*Password Not Matched(Please Enter Correct Password)*"
	  er1.style.color="red"
	  pass2.focus();
	  return false;
	}
}