$(document).ready(function(){
    $("#own-recipe").click(function(){
      $("#recipe-frm").toggle();
      $("#feedback-btn").css("display","none");

    });
    $("#review").click(function(){
		$("#review-frm").toggle();
		$("#rate-frm").css("display","none");
});
 $("#rate").click(function(){
		$("#rate-frm").toggle();
		$("#review-frm").css("display","none");
});
});

function hideButton(event){
	event.preventDefault();
	x=document.getElementById("rateNreview-btn").style.display="none";

}