$(document).ready(function(){
    $("#feedback-btn").click(function(){
      $("#feedback-frm").toggle();
      $("#feedback-btn").css("display","none");

    });
    $("#feedback-btn").click(function(){
      $("#section").css("display","none");
      
    });
    $("#submit").click(function(event){
      event.preventDefault();
      location.reload();
    });
    $("#back").one('click', function (event) {  
      event.preventDefault();
      location.reload();

    })
  });
  